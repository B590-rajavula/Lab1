package iu.b590.spring2025.midtermsection6

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import iu.b590.spring2025.midtermsection6.R
import iu.b590.spring2025.midtermsection6.databinding.FragmentNotesBinding
import iu.b590.spring2025.midtermsection6.model.Note
import kotlinx.coroutines.launch

private const val TAG = "NotesFragment"

class NotesFragment : Fragment() {

    // Binding for the fragment layout
    private var _binding: FragmentNotesBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null."
        }

    // ViewModel for managing notes data
    private val noteViewModel: NoteViewModel by viewModels()

    // Adapter for the RecyclerView to display notes
    private lateinit var noteAdapter: NoteAdapter

    // Inflates the fragment layout and sets up views
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)

        // Handle logout button click
        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()  // Sign out the user from Firebase
            findNavController().navigate(R.id.navigateToLogin)  // Navigate to Login screen
        }
        return binding.root
    }

    // Called after the view is created, sets up the RecyclerView and observes LiveData
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView with LinearLayoutManager
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the note adapter with empty list and click handlers
        noteAdapter = NoteAdapter(
            emptyList(), // Initialize with an empty list
            onNoteClicked = { note -> onNoteClicked(note) },  // Handle note click (navigate to NoteFragment)
            onDeleteClicked = { note -> onDeleteClicked(note) }  // Handle delete click (show confirmation dialog)
        )
        binding.recyclerView.adapter = noteAdapter

        // Collect notes data from the ViewModel and update RecyclerView
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteViewModel.notes.collect { notes ->
                    // Update the adapter with the new notes list
                    noteAdapter = NoteAdapter(
                        notes, // Initialize with the notes list
                        onNoteClicked = { note -> onNoteClicked(note) },
                        onDeleteClicked = { note -> onDeleteClicked(note) }
                    )
                    binding.recyclerView.adapter = noteAdapter
                }
            }
        }

        // Check if user is logged in
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            goToLoginScreen()  // If user is not logged in, navigate to login screen
        }

        // Handle adding new notes
        binding.addNoteButton.setOnClickListener {
            this.findNavController().navigate(R.id.navigateToNote)  // Navigate to create/edit note screen
        }
    }

    // Navigate to Login screen
    private fun goToLoginScreen() {
        this.findNavController().navigate(R.id.navigateToLogin)
    }

    // Handle note item click: navigate to NoteFragment with note details
    private fun onNoteClicked(note: Note) {
        val action = NotesFragmentDirections.navigateToNote(
            note.title,
            note.description,
            note.documentId
        )
        findNavController().navigate(action)
    }

    // Handle delete button click: Show confirmation dialog for deleting the note
    private fun onDeleteClicked(note: Note) {
        val dialog = DeleteConfirmationDialogFragment {
            deleteNote(note)  // Call deleteNote if confirmed
        }
        dialog.show(childFragmentManager, "DeleteConfirmationDialog")  // Show the dialog
    }

    // Delete the note from Firestore
    private fun deleteNote(note: Note) {
        val firestoreDb = FirebaseFirestore.getInstance()
        firestoreDb.collection("notes").document(note.documentId!!).delete()
            .addOnSuccessListener {
                Log.d(TAG, "Deleted note successfully")  // Log successful deletion
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Exception occurred: $e")  // Log any error that occurs
            }
    }

    // Clean up the binding when the view is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Nullify the binding to prevent memory leaks
    }
}
