package iu.b590.spring2025.practicum7

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import iu.b590.spring2025.practicum7.databinding.FragmentTicketListBinding

private const val TAG = "TicketListFragment"

class TicketListFragment : Fragment() {

    private var _binding: FragmentTicketListBinding? = null
    private val binding get() = checkNotNull(_binding) { "Cannot access binding because it is null" }

    private val ticketListViewModel: TicketListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTicketListBinding.inflate(inflater, container, false)
        binding.ticketRecyclerView.layoutManager = LinearLayoutManager(context)

        val adapter = TicketListAdapter(ticketListViewModel.tickets)
        binding.ticketRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

