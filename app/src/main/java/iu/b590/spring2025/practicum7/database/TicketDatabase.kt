package iu.b590.spring2025.practicum7.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import iu.b590.spring2025.practicum7.Ticket

@Database(entities = [Ticket::class], version = 1)
@TypeConverters(TicketTypeConverter::class)
abstract class TicketDatabase: RoomDatabase() {
    abstract fun ticketDao(): TicketDao
}