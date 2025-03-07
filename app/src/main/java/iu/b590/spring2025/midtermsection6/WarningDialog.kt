package iu.b590.spring2025.midtermsection6

import android.app.Dialog
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.DialogFragment


class  WarningDialog (
    private val onDeleteConfirmed: () -> Unit
    ) : DialogFragment() {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return AlertDialog.Builder(requireContext())
                .setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Delete") { _, _ -> onDeleteConfirmed() }
                .setNegativeButton("Cancel", null)
                .create()
        }
    }
