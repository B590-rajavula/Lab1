package iu.b590.spring2025.midtermsection6.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log
import com.google.firebase.firestore.toObjects

private const val TAG = "PostViewModel"
class NoteViewModel: ViewModel() {
    private val _notes: MutableStateFlow<List<Notes>> = MutableStateFlow(emptyList())
    val notes: StateFlow<List<Notes>>
        get() = _notes.asStateFlow()

    init {
        val firestoreDB = FirebaseFirestore.getInstance()
        var notesReference = firestoreDB
            .collection("Notes")
            .limit(30)
            .orderBy("title", Query.Direction.DESCENDING)
        viewModelScope.launch {
            notesReference.addSnapshotListener { snapshot, exception ->
                if (exception != null || snapshot == null) {
                    Log.e(TAG, "Exception when querying posts", exception)
                    return@addSnapshotListener
                }
                val noteList = snapshot.toObjects<Notes>()
                _notes.value = noteList as MutableList<Notes>
                for (post in noteList) {
                    Log.i(TAG,  "Post ${post}")
                }
            }
        }
    }
}