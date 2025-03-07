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

private const val TAG="NoteFragment"

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null."
        }

    private var signedInUser: User? = null
    private lateinit var firestoreDb: FirebaseFirestore

    // Safe Args
    private val args: NoteFragmentArgs by navArgs()

    // Store the note ID
    private var noteId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        firestoreDb = FirebaseFirestore.getInstance()

        // Populate the fields if arguments are passed
        binding.titleEditText.setText(args.noteTitle)
        binding.descriptionEditText.setText(args.noteDescription)

        // Retrieve noteId from arguments
        noteId = args.noteDocumentId

        binding.saveButton.setOnClickListener {
            saveTheNote()
        }
        getTheCurrentUser()
        return binding.root
    }

    private fun getTheCurrentUser() {
        Log.i(TAG, "inside getcurrent user")
        firestoreDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                Log.i(TAG, "signed in user: $signedInUser")
            }
            .addOnFailureListener { exception ->
                Log.i(TAG, "Failure fetching signed in user", exception)
            }
    }

    private fun saveTheNote() {
        val title = binding.titleEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()

        if (noteId != null) {
            // Update existing note
            val noteValues = mapOf(
                "title" to title,
                "description" to description
            )

            firestoreDb.collection("notes")
                .document(noteId!!) // Use non-null asserted noteId
                .update(noteValues)
                .addOnCompleteListener {
                    this.findNavController().navigate(R.id.navigateToNotes)
                }
        } else {
            // Create a new note
            val note = Note(
                description,
                signedInUser,
                title,
                System.currentTimeMillis()
            )
            firestoreDb.collection("notes").add(note).addOnSuccessListener { documentReference ->
                // After successfully adding the note, get the document ID
                val newNoteId = documentReference.id
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                this.findNavController().navigate(R.id.navigateToNotes) // Navigate back
            }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }
    }
}