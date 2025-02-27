package iu.b590.spring2025.practicum7

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import iu.b590.spring2025.practicum7.databinding.FragmentTicketDetailBinding
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

private const val TAG = "TicketDetailFragment"
class TicketDetailFragment: Fragment() {
    private val args:TicketDetailFragmentArgs by navArgs()
    private val ticketDetailViewModel: TicketDetailViewModel by viewModels {
        TicketDetailViewModelFactory(args.ticketId)
    }
    private var _binding: FragmentTicketDetailBinding? = null
    private val binding get() = checkNotNull(_binding){
        "Cannot access binding because it is null. Is the view visible?"
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
        _binding= FragmentTicketDetailBinding.inflate(layoutInflater,container,false)
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
        binding.apply {
            ticketTitle.doOnTextChanged { text, _, _, _ ->
//                ticket = ticket.copy(title = text.toString())
//                ticket.copy(title = text.toString())
            }
            ticketDate.apply{
//                text = ticket.date.toString()
//                ticket.date.toString()
                isEnabled = false
            }
            ticketSolved.setOnCheckedChangeListener { _, isChecked ->
//                ticket = ticket.copy(isSolved = isChecked)
//                ticket.copy(isSolved = isChecked)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
           viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
               ticketDetailViewModel.ticket.collect{
                   ticket -> ticket?.let{updateUi(it)}
               }
           }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi(ticket: Ticket) {
        binding.apply {
            if(ticketTitle.text.toString() != ticket.title) {
                ticketTitle.setText(ticket.title)
            }
            ticketDate.text = ticket.date.toString()
            ticketSolved.isChecked = ticket.isSolved
        }
    }
}