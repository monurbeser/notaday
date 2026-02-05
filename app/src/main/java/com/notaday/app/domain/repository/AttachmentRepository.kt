package com.notaday.app.domain.repository

import com.notaday.app.domain.model.Attachment
import kotlinx.coroutines.flow.Flow

interface AttachmentRepository {
    fun getAttachmentsByNoteId(noteId: Long): Flow<List<Attachment>>
    suspend fun getAttachmentById(id: Long): Attachment?
    suspend fun insertAttachment(attachment: Attachment): Long
    suspend fun insertAttachments(attachments: List<Attachment>)
    suspend fun deleteAttachment(attachment: Attachment)
    suspend fun deleteAttachmentById(id: Long)
    suspend fun getAttachmentCountForNote(noteId: Long): Int
}
