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

private const val TAG="NotesFragment"

class NotesFragment : Fragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null."
        }

    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var noteAdapter: NoteAdapter // Declare NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)

        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.navigateToLogin)
//            To Navigate to login Page
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView and Adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        noteAdapter = NoteAdapter(
            emptyList(), // Initialize with an empty list
            onNoteClicked = { note ->
                // Handle note click (navigate to NoteFragment)
                onNoteClicked(note)
            },
            onDeleteClicked = { note ->
                // Handle delete click (show confirmation dialog)
                onDeleteClicked(note)
            }
        )
        binding.recyclerView.adapter = noteAdapter


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteViewModel.notes.collect { notes ->
                    noteAdapter = NoteAdapter(
                        notes, // Initialize with the notes list
                        onNoteClicked = { note ->
                            // Handle note click (navigate to NoteFragment)
                            onNoteClicked(note)
                        },
                        onDeleteClicked = { note ->
                            // Handle delete click (show confirmation dialog)
                            onDeleteClicked(note)
                        }
                    )
                    binding.recyclerView.adapter = noteAdapter
                }
            }
        }

        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            goToLoginScreen()
        }

        binding.addNoteButton.setOnClickListener {
            this.findNavController().navigate(R.id.navigateToNote)
        }
    }

    private fun goToLoginScreen() {
        this.findNavController().navigate(R.id.navigateToLogin)
    }


    private fun onNoteClicked(note: Note) {
        // Navigate to NoteFragment, passing the note data
        val action = NotesFragmentDirections.navigateToNote(
            note.title,
            note.description,
            note.documentId
        )
        findNavController().navigate(action)
    }

    private fun onDeleteClicked(note: Note) {
        // Show confirmation dialog fragment
        val dialog = DeleteConfirmationDialogFragment {
            deleteNote(note)  // Call deleteNote if confirmed
        }
        dialog.show(childFragmentManager, "DeleteConfirmationDialog")
    }

    private fun deleteNote(note: Note) {
        val firestoreDb = FirebaseFirestore.getInstance()
        firestoreDb.collection("notes").document(note.documentId!!).delete()
            .addOnSuccessListener {
                Log.d(TAG, "Deleted note successfully")

            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Exception occured:$e")
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}