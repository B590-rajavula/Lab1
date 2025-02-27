package iu.b590.spring2025.practicum7

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import iu.b590.spring2025.practicum7.databinding.ListItemTicketBinding
import iu.b590.spring2025.practicum7.databinding.ListItemManagerTicketBinding

class TicketListAdapter(private val tickets: List<Ticket>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (tickets[position].requiresManager) {
            1  // Manager ticket
        } else {
            0  // Normal ticket
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            1 -> {
                val binding = ListItemManagerTicketBinding.inflate(inflater, parent, false)
                ManagerTicketHolder(binding)
            }
            else -> {
                val binding = ListItemTicketBinding.inflate(inflater, parent, false)
                TicketHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return tickets.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val ticket = tickets[position]
        when (holder) {
            is TicketHolder -> holder.bind(ticket)
            is ManagerTicketHolder -> holder.bind(ticket)
        }
    }
}

class TicketHolder(val binding: ListItemTicketBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(ticket: Ticket) {
        binding.ticketTitle.text = ticket.title
        binding.ticketDate.text = ticket.date.toString()
        binding.root.setOnClickListener {
            Toast.makeText(binding.root.context, "${ticket.title} clicked", Toast.LENGTH_SHORT).show()
        }
    }
}

class ManagerTicketHolder(val binding: ListItemManagerTicketBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(ticket: Ticket) {
        binding.ticketTitle.text = ticket.title
        binding.ticketDate.text = ticket.date.toString()

        // Set up the "Contact Manager" button
        binding.contactManagerButton.setOnClickListener {
            // Handle "Contact Manager" button click
            Toast.makeText(binding.root.context, "Contacting manager for ${ticket.title}", Toast.LENGTH_SHORT).show()
        }
    }
}


