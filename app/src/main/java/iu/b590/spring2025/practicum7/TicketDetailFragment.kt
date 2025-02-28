package iu.b590.spring2025.practicum7

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import iu.b590.spring2025.practicum7.databinding.FragmentTicketDetailBinding
import kotlinx.coroutines.launch
import java.io.File
import java.util.Date
import java.util.UUID

private const val TAG = "TicketDetailFragment"
private const val DATE_FORMAT = "EEE, MMM, dd"
class TicketDetailFragment: Fragment() {
    private val args: TicketDetailFragmentArgs by navArgs()
    private val ticketDetailViewModel: TicketDetailViewModel by viewModels {
        TicketDetailViewModelFactory(args.ticketId)
    }
    private var _binding: FragmentTicketDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val selectAssignee = registerForActivityResult(
        ActivityResultContracts.PickContact()
    ) { uri: Uri? ->
        //Read
        uri?.let { parseContactSelection(it) }
    }


    private val takePhoto = registerForActivityResult(
        ActivityResultContracts. TakePicture()
    ) { didTakePhoto: Boolean ->
// Handle the result
    }

//    private lateinit var ticket: Ticket

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        ticket = Ticket(
//            id= UUID.randomUUID(),
//            title="",
//            date= Date(),
//            isSolved=false
//        )
//        Log.d(TAG, "The ticket id is ${args.ticketId}")
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTicketDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val ticket = Ticket(
//            id = args.ticketId,  // Using the passed ticket ID
//            title = "",  // You can fill in details as needed
//            date = Date(),  // Set current date or update as needed
//            isSolved = false  // Update with real data if available
//        )
        binding.toolbar.inflateMenu(R.menu.fragment_ticket_detail)
        binding.apply {
            ticketTitle.doOnTextChanged { text, _, _, _ ->
//                ticket = ticket.copy(title = text.toString())
//                ticket.copy(title = text.toString())
                ticketDetailViewModel.updateTicket { oldTicket ->
                    oldTicket.copy(title = text.toString())
                }
            }
//            ticketDate.apply{
////                text = ticket.date.toString()
////                ticket.date.toString()
//                isEnabled = false
//            }
            ticketSolved.setOnCheckedChangeListener { _, isChecked ->
//                ticket = ticket.copy(isSolved = isChecked)
//                ticket.copy(isSolved = isChecked)
                ticketDetailViewModel.updateTicket { oldTicket ->
                    oldTicket.copy(isSolved = isChecked)
                }

            }
            ticketAssignee.setOnClickListener {
                selectAssignee.launch(null)
            }

            val selectAssigneeIntent = selectAssignee.contract.createIntent(
                requireContext(),
                null
            )
            ticketAssignee.isEnabled = canResolveIntent(selectAssigneeIntent)

            ticketCamera.setOnClickListener {
                val photoName = "IMG_${Date()}.JPG"
                val photoFile = File(requireContext().applicationContext.filesDir, photoName)
                val photoUri = FileProvider.getUriForFile(
                    requireContext(),
                    "iu.b590.spring2025.practicum7.fileprovider",
                    photoFile
                )
                takePhoto.launch(photoUri)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                ticketDetailViewModel.ticket.collect { ticket ->
                    ticket?.let { updateUi(it) }
                }
            }
        }

        setFragmentResultListener(
            DatePickerFragment.REQUEST_KEY_DATE
        ) { _, bundle ->
            val newDate =
                bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date
            ticketDetailViewModel.updateTicket { it.copy(date = newDate) }
        }

        setFragmentResultListener(
            TimePickerFragment.REQUEST_KEY_TIME
        ) { _, bundle ->
            val newDate =
                bundle.getSerializable(TimePickerFragment.BUNDLE_KEY_TIME) as Date
            ticketDetailViewModel.updateTicket { it.copy(date = newDate) }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi(ticket: Ticket) {
        binding.apply {
            if (ticketTitle.text.toString() != ticket.title) {
                ticketTitle.setText(ticket.title)
            }
            ticketDate.text = ticket.date.toString()
            ticketDate.setOnClickListener {
                findNavController().navigate(TicketDetailFragmentDirections.selectDate(ticket.date))
            }
            ticketTime.text = "Pick the time"
            ticketTime.setOnClickListener {
                // Navigate to TimePickerFragment
                findNavController().navigate(TicketDetailFragmentDirections.selectTime(ticket.date))
            }
            ticketSolved.isChecked = ticket.isSolved

            ticketReport.setOnClickListener {
                val reportIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, getTicketReport(ticket))
                    putExtra(
                        Intent.EXTRA_SUBJECT,
                        getString(R.string.ticket_report_subject)
                    )
                }
                val chooserIntent = Intent.createChooser(
                    reportIntent,
                    getString(R.string.send_report)
                )
                startActivity(chooserIntent)
            }
            ticketAssignee.text = ticket.assignee.ifEmpty {
                getString(R.string.ticket_assignee_text)
            }
        }
    }

    private fun getTicketReport(ticket: Ticket): String {
        val solvedString = if (ticket.isSolved) {
            getString(R.string.ticket_report_solved)
        } else {
            getString(R.string.ticket_report_unsolved)
        }

        val dateString = DateFormat.format(DATE_FORMAT, ticket.date).toString()
        val assigneeText = if (ticket.assignee.isBlank()) {
            getString(R.string.ticket_report_no_assignee)
        } else {
            getString(R.string.ticket_report_assignee, ticket.assignee)
        }

        return getString(

            R.string.ticket_report,
            ticket.title, dateString, solvedString, assigneeText
        )
    }

    private fun parseContactSelection(contactUri: Uri) {
        val queryFields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
        val queryCursor = requireActivity().contentResolver
            .query(
                contactUri, queryFields,
                null, null,
                null
            )
        queryCursor?.use { cursor ->
            if (cursor.moveToFirst()) {
                val assignee = cursor.getString(0)
                ticketDetailViewModel.updateTicket { oldTicket ->
                    oldTicket.copy(assignee = assignee)
                }
            }
        }

    }


    private fun canResolveIntent(intent: Intent): Boolean {
        val packageManager: PackageManager = requireActivity().packageManager
        val resolvedActivity: ResolveInfo? =
            packageManager.resolveActivity(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
        return resolvedActivity != null
    }
}
