package iu.b590.spring2025.midtermsection6

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

// A DialogFragment that shows a confirmation dialog for deleting a note.
class DeleteConfirmationDialogFragment(private val onConfirm: () -> Unit) : DialogFragment() {

    // Override the onCreateDialog method to build and return the dialog.
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())  // Create an AlertDialog.Builder using the fragment's context
            .setTitle("Delete Note")  // Set the dialog's title
            .setMessage("Are you sure you want to delete this note?")  // Set the dialog's message
            .setPositiveButton("Delete") { _, _ ->  // Set the positive button (Delete)
                onConfirm() // Invoke the onConfirm callback when the user clicks "Delete"
            }
            .setNegativeButton("Cancel", null)  // Set the negative button (Cancel), no action when clicked
            .create()  // Create and return the dialog
    }
}
