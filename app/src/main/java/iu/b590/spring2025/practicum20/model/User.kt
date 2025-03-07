package iu.b590.spring2025.practicum20.model

import com.google.firebase.firestore.PropertyName

data class User(
    var username: String = "",
    var age: String = "",
    @get: PropertyName("profile_url") @set: PropertyName("profile_url")
    var profileUrl: String = "",
)
