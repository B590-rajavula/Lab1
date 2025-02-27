package iu.b590.spring2025.practicum7

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

private  const val  TAG = "TicketListViewModel"

class TicketListViewModel: ViewModel() {

    private val ticketRepository = TicketRepository.get()

    private val _tickets: MutableStateFlow<List<Ticket>> = MutableStateFlow(emptyList())

    val tickets: StateFlow<List<Ticket>>

        get() = _tickets.asStateFlow()
//    val tickets = mutableListOf<Ticket>()

    init {
//        Log.d(TAG, "init starting")
        viewModelScope.launch {
//            Log.d(TAG, "coroutine launched")
//                tickets += loadTickets()
//            Log.d(TAG, "Loading tickets finished")
            ticketRepository.getTickets().collect {
                _tickets.value = it
            }
            }
        }
//    suspend fun loadTickets(): List<Ticket>{
//        val result = mutableListOf<Ticket>()
//        delay(5000)
//        for (i in 0 until 100) {
//            val ticket = Ticket(
//                id = UUID.randomUUID(),
//                title = "ticket #$i",
//                date = Date(),
//                isSolved = i % 2 == 0,
//                requiresManager = i % 5 == 0 // Randomly mark every 5th ticket as requiring manager
//            )
//            result += ticket
//    }
//        return result
//    }
}

