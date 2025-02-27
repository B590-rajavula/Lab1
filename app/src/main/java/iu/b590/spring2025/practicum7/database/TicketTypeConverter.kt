package iu.b590.spring2025.practicum7.database

import androidx.room.TypeConverter
import java.util.Date
import java.util.UUID

class TicketTypeConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    //    @TypeConverter
//    fun fromUUID(uuid: String?): UUID? {
//        return uuid?.let { UUID.fromString(it) }
//    }
//
//    @TypeConverter
//    fun uuidToString(uuid: UUID?): String? {
//        return uuid?.toString()
//    }
    @TypeConverter
    fun fromUUID(id: UUID?): String {
        return id.toString()
    }

    @TypeConverter
    fun toUUID(idString: String): UUID {
        return UUID.fromString((idString))
    }
}
