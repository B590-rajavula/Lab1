package iu.b590.spring2025.midtermsection6

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import iu.b590.spring2025.midtermsection6.databinding.FragmentLoginBinding

private const val TAG="LoginFragment"

class LoginFragment : Fragment() {

    // Private binding variable to handle the fragment's views
    private var _binding: FragmentLoginBinding? = null

    // Getter for binding, throws an error if the binding is null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null."
        }

    // onCreateView is called when the fragment's view is created
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment's layout and bind the views
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        // Get an instance of FirebaseAuth
        val auth = FirebaseAuth.getInstance()

        // If a user is already logged in, navigate directly to the Notes screen
        if (auth.currentUser != null) {
            goToNotesScreen()
        }

        // Set up the login button click listener
        binding.btnLogin.setOnClickListener {
            binding.btnLogin.isEnabled = false  // Disable the button to prevent multiple clicks

            // Get the email and password entered by the user
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            // Validate input - check if email or password is empty
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this.context, "Email/password cannot be empty", Toast.LENGTH_SHORT).show()
                binding.btnLogin.isEnabled = true  // Re-enable the button if validation fails
                return@setOnClickListener
            }

            // Attempt to sign in the user with email and password
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                binding.btnLogin.isEnabled = true  // Re-enable the button after the task completes

                // Check if sign-in was successful
                if (task.isSuccessful) {
                    Toast.makeText(this.context, "Success!!", Toast.LENGTH_SHORT).show()
                    goToNotesScreen()  // Navigate to the Notes screen if successful
                } else {
                    // Log the error and show a failure message
                    Log.e(TAG, "signInWithEmail failed", task.exception)
                    Toast.makeText(this.context, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Return the root view of the binding to display the fragment's UI
        return binding.root
    }

    // Function to navigate to the Notes screen
    private fun goToNotesScreen() {
        this.findNavController().navigate(R.id.navigateToNotes)
    }
}
