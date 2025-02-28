package iu.b590.spring2025.lab7

import android.content.Context
import androidx.room.Room
import iu.b590.spring2025.lab7.database.TicketDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TicketRepository private constructor(
    context: Context
) {
    private val database: TicketDatabase = Room.databaseBuilder(
        context.applicationContext,
        TicketDatabase::class.java,
        "ticket-database"
    ).build()

    fun getTickets() = database.ticketDao().getTickets()

    fun getTicket(id: java.util.UUID) = database.ticketDao().getTicket(id)

    fun updateTicket(ticket: Ticket) {
        GlobalScope.launch {
            database.ticketDao().updateTicket(ticket)
        }
    }

    fun addTicket(ticket: Ticket) {
        GlobalScope.launch {
            database.ticketDao().addTicket(ticket)
        }
    }

    fun deleteTicket(ticket: Ticket) {
        GlobalScope.launch {
            database.ticketDao().deleteTicket(ticket)
        }
    }

    companion object {
        private var INSTANCE: TicketRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = TicketRepository(context)
            }
        }

        fun get(): TicketRepository {
            return INSTANCE ?: throw IllegalStateException("TicketRepository must be initialized")
        }
    }
}
