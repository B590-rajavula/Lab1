package iu.b590.spring2025.midtermsection6


import android.nfc.Tag
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

private const val Tag="NoteViewModel"
class NoteViewModel:ViewModel() {

    private val _notes: MutableStateFlow<List<Note>> = MutableStateFlow(emptyList())
    val notes:StateFlow<List<Note>>
        get()=_notes.asStateFlow()

    init {
        val firestoreDB=FirebaseFirestore.getInstance()
        val notesReference = firestoreDB.collection("notes")
            .limit(30)
            .orderBy("creation_time", Query.Direction.DESCENDING)

        viewModelScope.launch {
            notesReference.addSnapshotListener { snapshot, exception ->
                if(exception!=null || snapshot == null){
                    Log.e(Tag, "Exception when querying notes", exception)
                    return@addSnapshotListener
                }
                val noteList=snapshot.documents.map { document ->
                    val note = document.toObject(Note::class.java)!!
                    note.documentId = document.id  // Correctly set documentId here
                    note
                }
                _notes.value=noteList as MutableList<Note>
                for(note in noteList) {
                    Log.i(Tag, "Note ${note}")
                }
            }
        }

    }
}