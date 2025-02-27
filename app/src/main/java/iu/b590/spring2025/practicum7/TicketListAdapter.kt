package iu.b590.spring2025.practicum7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import iu.b590.spring2025.practicum7.databinding.ListItemTicketBinding


class TicketHolder(val binding: ListItemTicketBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(ticket: Ticket) {
        binding.ticketTitle.text = ticket.title
        binding.ticketDate.text = ticket.date.toString()

        binding.root.setOnClickListener {
            Toast.makeText(binding.root.context, "${ticket.title} clicked!", Toast.LENGTH_SHORT).show()
        }
        binding.ticketSolved.visibility = if (ticket.isSolved) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

class TicketListAdapter(
    private val tickets: List<Ticket>
) : RecyclerView.Adapter<TicketHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemTicketBinding.inflate(inflater, parent, false)
        return TicketHolder(binding)
    }

    override fun getItemCount(): Int {
        return tickets.size
    }

    override fun onBindViewHolder(holder: TicketHolder, position: Int) {
        val ticket = tickets[position]
        holder.bind(ticket)
    }
}