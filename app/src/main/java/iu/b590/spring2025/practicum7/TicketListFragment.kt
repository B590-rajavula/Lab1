package iu.b590.spring2025.practicum7

import android.os.Bundle
//import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import iu.b590.spring2025.practicum7.databinding.FragmentTicketListBinding
//import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

//private const val TAG = "TicketListFragment"

class TicketListFragment : Fragment() {

    private var _binding: FragmentTicketListBinding? = null
    private val binding get() = checkNotNull(_binding) { "Cannot access binding because it is null" }

    private val ticketListViewModel: TicketListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketListBinding.inflate(inflater, container, false)
        binding.ticketRecyclerView.layoutManager = LinearLayoutManager(context)
//
//        val adapter = TicketListAdapter(ticketListViewModel.tickets)
//        binding.ticketRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED) {
//                val tickets = ticketListViewModel.tickets
//                val adapter = TicketListAdapter(tickets)
//                binding.ticketRecyclerView.adapter = adapter
                ticketListViewModel.tickets.collect{ tickets ->
                    binding.ticketRecyclerView.adapter = TicketListAdapter(tickets)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

