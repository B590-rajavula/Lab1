package iu.b590.spring2025.practicum20

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import iu.b590.spring2025.practicum20.model.User
import iu.b590.spring2025.practicum20.model.Post
import iu.b590.spring2025.practicum20.databinding.FragmentCreateBinding
import iu.b590.spring2025.practicum20.databinding.FragmentPostsBinding


private const val TAG = "CreateFragment"
class CreateFragment: Fragment() {
    private var _binding: FragmentCreateBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null."
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Log.i(TAG, "No user is currently signed in.")
        } else {
            Log.i(TAG, "User signed in: ${user.uid}")
        }
    }

    private var photoUri: Uri? = null
    private var signedInUser: User? = null
    private lateinit var firestoreDb: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// Inflate the layout for this fragment
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        firestoreDb = FirebaseFirestore.getInstance()
        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
// Callback is invoked after the user selects a media item or closes the
// photo picker.
                if (uri != null) {
                    Log.d(TAG, "Selected URI: $uri")
                    photoUri = uri
                    binding.imageView.setImageURI(uri)
                } else {
                    Log.d(TAG, "No media selected")
                }
            }

        binding.btnPickImage.setOnClickListener {
            Log.i(TAG, "Open up image picker on device")
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.btnSubmit.setOnClickListener {
            saveThePost()
        }
        getTheCurrentUser()
        return binding.root
    }

    private fun getTheCurrentUser() {
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
    private fun saveThePost(){
        val post = Post(

        binding.etDescription.text.toString(),
       "",
        System.currentTimeMillis(),
        signedInUser
        )
        firestoreDb.collection(  "posts").add(post).addOnCompleteListener{
            this.findNavController().navigate(R.id.navigate_to_postsFragment)
        }
    }
}
