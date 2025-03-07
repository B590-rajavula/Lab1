package iu.b590.spring2025.midtermsection6.model

import com.google.firebase.firestore.PropertyName
import iu.b590.spring2025.midtermsection6.model.User

data class Note(
    var description: String="",
    var user: User?=null,
    var title: String="",
    @get:PropertyName("creation_time") @set:PropertyName("creation_time")
    var creation_time:Long=0,
    var documentId: String? = null
)
