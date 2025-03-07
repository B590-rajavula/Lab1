package iu.b590.spring2025.midtermsection6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import iu.b590.spring2025.midtermsection6.databinding.FragmentNotesBinding
import iu.b590.spring2025.midtermsection6.model.NoteViewModel
import kotlinx.coroutines.launch


private const val TAG = "NotesFragment"

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null."
        }

    private  val noteViewModel: NoteViewModel by viewModels()
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// Inflate the layout for this fragment
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesAdapter = NotesAdapter(mutableListOf()) // Initialize with an empty list
        binding.rvPosts.adapter = notesAdapter  // Set adapter once

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteViewModel.notes.collect { notes ->
                    notesAdapter.updateNotes(notes) // Update instead of replacing
                }
            }
        }
        // Check if user is logged in, else go to login screen
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            goToLoginScreen()
        }
        binding.fabCreate.setOnClickListener {
            this.findNavController().navigate(R.id.navigate_to_createFragment)
        }
    }

//    // Inflates the menu to add the logout option
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu_posts, menu) // Inflate your menu
//        super.onCreateOptionsMenu(menu, inflater)
//    }


    // Handle the logout action from the toolbar
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.menu_logout -> {
//                FirebaseAuth.getInstance().signOut()
//                goToLoginScreen()
//                return true
//            }
//            else -> return super.onOptionsItemSelected(item)
//        }
//    }

    private fun goToLoginScreen() {
        this.findNavController().navigate(R.id.navigateToLogin)
    }
}
