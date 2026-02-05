package com.notaday.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.notaday.app.data.local.database.Converters
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "notes")
@TypeConverters(Converters::class)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val date: LocalDate,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val isTodo: Boolean = false,
    val isCompleted: Boolean? = null,
    val deadline: LocalDateTime? = null,
    val reminderTime: LocalDateTime? = null,
    val priority: Priority? = null
)

enum class Priority {
    LOW, MEDIUM, HIGH
}
