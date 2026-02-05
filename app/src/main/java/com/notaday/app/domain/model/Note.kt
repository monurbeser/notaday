package com.notaday.app.domain.model

import com.notaday.app.data.local.entities.Priority
import java.time.LocalDate
import java.time.LocalDateTime

data class Note(
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
    val priority: Priority? = null,
    val attachments: List<Attachment> = emptyList()
)
