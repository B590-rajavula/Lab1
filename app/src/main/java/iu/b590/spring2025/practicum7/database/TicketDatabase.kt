package iu.b590.spring2025.practicum7.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import iu.b590.spring2025.practicum7.Ticket

@Database(entities = [Ticket::class], version = 3)
@TypeConverters(TicketTypeConverter::class)
abstract class TicketDatabase: RoomDatabase() {
    abstract fun ticketDao(): TicketDao
}

val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Ticket ADD COLUMN assignee TEXT NOT NULL DEFAULT ''"
        )
    }
}

val migration_2_3 = object: Migration (2,3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Ticket ADD COLUMN photoFileName TEXT"
        )
    }
}

