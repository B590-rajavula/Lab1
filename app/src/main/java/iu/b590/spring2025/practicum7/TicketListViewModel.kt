package iu.b590.spring2025.practicum7

import androidx.lifecycle.ViewModel
import java.util.Date
import java.util.UUID

class TicketListViewModel: ViewModel() {
    val tickets = mutableListOf<Ticket>()
    init{
        for(i in 0 until 100) {
            val ticket = Ticket(
                id = UUID.randomUUID(),
                title = "ticket #$i",
                date = Date(),
                isSolved = i % 2 == 0,
                requiresManager = i % 5 == 0 // Randomly mark every 5th ticket as requiring manager
            )
            tickets += ticket
        }
    }
}

