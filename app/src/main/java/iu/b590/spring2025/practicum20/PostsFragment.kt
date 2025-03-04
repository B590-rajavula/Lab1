package iu.b590.spring2025.practicum20

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.fragment.findNavController
import iu.b590.spring2025.practicum20.databinding.FragmentPostsBinding
import kotlinx.coroutines.launch
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater

class PostsFragment : Fragment() {

    private var _binding: FragmentPostsBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null."
        }
    private val postViewModel: PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)

        // Set up the Toolbar as the action bar
        setHasOptionsMenu(true)

        // Initialize the toolbar
        val toolbar = binding.root.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Collect posts data
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                postViewModel.posts.collect { posts ->
                    binding.rvPosts.adapter = PostsAdapter(posts)
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

    // Inflates the menu to add the logout option
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_posts, menu) // Inflate your menu
        super.onCreateOptionsMenu(menu, inflater)
    }


    // Handle the logout action from the toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                FirebaseAuth.getInstance().signOut()
                goToLoginScreen()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun goToLoginScreen() {
        this.findNavController().navigate(R.id.navigateToLogin)
    }
}

