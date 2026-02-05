package com.notaday.app.data.local.entities

import com.notaday.app.domain.model.Attachment
import com.notaday.app.domain.model.Note

fun NoteEntity.toDomain(attachments: List<Attachment> = emptyList()): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        date = date,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isTodo = isTodo,
        isCompleted = isCompleted,
        deadline = deadline,
        reminderTime = reminderTime,
        priority = priority,
        attachments = attachments
    )
}

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        content = content,
        date = date,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isTodo = isTodo,
        isCompleted = isCompleted,
        deadline = deadline,
        reminderTime = reminderTime,
        priority = priority
    )
}

fun AttachmentEntity.toDomain(): Attachment {
    return Attachment(
        id = id,
        noteId = noteId,
        filePath = filePath,
        fileType = fileType,
        fileName = fileName,
        createdAt = createdAt
    )
}

fun Attachment.toEntity(): AttachmentEntity {
    return AttachmentEntity(
        id = id,
        noteId = noteId,
        filePath = filePath,
        fileType = fileType,
        fileName = fileName,
        createdAt = createdAt
    )
}
