package iu.b590.spring2025.practicum7

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TicketListViewModel : ViewModel() {
    private val ticketRepository = TicketRepository.get()
    private val _tickets = MutableStateFlow<List<Ticket>>(emptyList())
    val tickets: StateFlow<List<Ticket>> = _tickets.asStateFlow()

    init {
        viewModelScope.launch {
            ticketRepository.getTickets().collect { ticketList ->
                _tickets.value = ticketList
            }
        }
    }
}
