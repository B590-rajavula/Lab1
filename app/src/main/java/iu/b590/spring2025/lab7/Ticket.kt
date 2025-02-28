package iu.b590.spring2025.lab7

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID
@Entity
data class Ticket (
    @PrimaryKey var id: UUID,
    var title: String,
    var date: Date,
    var isSolved: Boolean
)


