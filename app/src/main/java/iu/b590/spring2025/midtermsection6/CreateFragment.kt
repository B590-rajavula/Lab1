package iu.b590.spring2025.midtermsection6

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import iu.b590.spring2025.midtermsection6.databinding.FragmentCreateBinding
import iu.b590.spring2025.midtermsection6.model.Notes


private const val TAG = "CreateFragment"
class CreateFragment: Fragment() {
    private var _binding: FragmentCreateBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null."
        }

//    private var signedInUser: User? = null
    private lateinit var firestoreDb: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = FirebaseAuth.getInstance().currentUser

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// Inflate the layout for this fragment
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        firestoreDb = FirebaseFirestore.getInstance()
        binding.btnSubmit.setOnClickListener {
            saveThePost()
        }
        return binding.root
                }

    private fun saveThePost() {
        val notes = Notes (
            binding.etTitle.text.toString(),
            binding.etDescription.text.toString()
        )
        firestoreDb.collection("Notes").add(notes).addOnCompleteListener{
            this.findNavController().navigate(R.id.navigate_To_NotesFragment)
        }
    }
            }