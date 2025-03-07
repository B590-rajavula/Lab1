package iu.b590.spring2025.midtermsection6

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObjects
import iu.b590.spring2025.midtermsection6.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val Tag = "NoteViewModel"

class NoteViewModel : ViewModel() {

    // MutableStateFlow to hold the list of notes (private)
    private val _notes: MutableStateFlow<List<Note>> = MutableStateFlow(emptyList())

    // Public read-only StateFlow to expose notes to the UI layer
    val notes: StateFlow<List<Note>>
        get() = _notes.asStateFlow()  // Convert to immutable flow to prevent external modification

    // Initialize the ViewModel and start listening to Firestore changes
    init {
        val firestoreDB = FirebaseFirestore.getInstance()

        // Reference to the "notes" collection in Firestore, with a limit of 30 documents, sorted by creation_time
        val notesReference = firestoreDB.collection("notes")
            .limit(30)
            .orderBy("creation_time", Query.Direction.DESCENDING)

        // Launch a coroutine to handle Firestore data asynchronously
        viewModelScope.launch {
            // Listen for changes in the "notes" collection in real-time
            notesReference.addSnapshotListener { snapshot, exception ->
                // Handle any errors encountered during the snapshot query
                if (exception != null || snapshot == null) {
                    Log.e(Tag, "Exception when querying notes", exception)
                    return@addSnapshotListener
                }

                // Process the documents from the snapshot into a list of Note objects
                val noteList = snapshot.documents.map { document ->
                    // Convert Firestore document into a Note object
                    val note = document.toObject(Note::class.java)!!

                    // Set the document ID to the note object
                    note.documentId = document.id

                    // Return the note object
                    note
                }

                // Update the _notes MutableStateFlow with the new list of notes
                _notes.value = noteList as MutableList<Note>  // Cast to MutableList to match type
                for (note in noteList) {
                    // Log the note information for debugging purposes
                    Log.i(Tag, "Note ${note}")
                }
            }
        }
    }
}
