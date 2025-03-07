package iu.b590.spring2025.midtermsection6

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import iu.b590.spring2025.midtermsection6.R
import iu.b590.spring2025.midtermsection6.databinding.FragmentNoteBinding
import iu.b590.spring2025.midtermsection6.model.Note
import iu.b590.spring2025.midtermsection6.model.User

private const val TAG = "NoteFragment"

class NoteFragment : Fragment() {

    // Private binding variable to handle the fragment's views
    private var _binding: FragmentNoteBinding? = null

    // Getter for binding, throws an error if the binding is null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null."
        }

    // Variable to store the current signed-in user
    private var signedInUser: User? = null

    // Firestore instance to interact with the database
    private lateinit var firestoreDb: FirebaseFirestore

    // Safe Args to retrieve passed arguments to the fragment
    private val args: NoteFragmentArgs by navArgs()

    // Store the note ID (if editing an existing note)
    private var noteId: String? = null

    // Called when the fragment's view is created
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout and bind the views
        _binding = FragmentNoteBinding.inflate(inflater, container, false)

        // Initialize Firestore database instance
        firestoreDb = FirebaseFirestore.getInstance()

        // If arguments are passed, populate the note's title and description
        binding.titleEditText.setText(args.noteTitle)
        binding.descriptionEditText.setText(args.noteDescription)

        // Retrieve the note ID from the arguments
        noteId = args.noteDocumentId

        // Set a listener on the save button to save the note
        binding.saveButton.setOnClickListener {
            saveTheNote() // Call the function to save the note
        }

        // Retrieve the current signed-in user
        getTheCurrentUser()

        return binding.root
    }

    // Function to fetch the current signed-in user from Firestore
    private fun getTheCurrentUser() {
        Log.i(TAG, "inside getcurrent user")
        firestoreDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid ?: "")
            .get()
            .addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                Log.i(TAG, "signed in user: $signedInUser")
            }
            .addOnFailureListener { exception ->
                Log.i(TAG, "Failure fetching signed in user", exception)
            }
    }

    // Function to save the note (either update or create a new one)
    private fun saveTheNote() {
        // Get the title and description from the input fields
        val title = binding.titleEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()

        if (noteId != null) {
            // Update an existing note if noteId is available
            val noteValues = mapOf(
                "title" to title,
                "description" to description
            )

            firestoreDb.collection("notes")
                .document(noteId!!) // Use the non-null asserted noteId
                .update(noteValues) // Update the note document
                .addOnCompleteListener {
                    // Navigate back to the Notes screen after saving
                    this.findNavController().navigate(R.id.navigateToNotes)
                }
        } else {
            // Create a new note if no noteId is provided
            val note = Note(
                description,
                signedInUser,
                title,
                System.currentTimeMillis() // Use current timestamp as creation time
            )
            firestoreDb.collection("notes").add(note)
                .addOnSuccessListener { documentReference ->
                    // After successfully adding the note, retrieve the document ID
                    val newNoteId = documentReference.id
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    // Navigate back to the Notes screen after saving
                    this.findNavController().navigate(R.id.navigateToNotes)
                }
                .addOnFailureListener { e ->
                    // Log an error if the note creation fails
                    Log.w(TAG, "Error adding document", e)
                }
        }
    }
}
