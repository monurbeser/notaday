package com.notaday.app.data.repository

import com.notaday.app.data.local.dao.AttachmentDao
import com.notaday.app.data.local.entities.toDomain
import com.notaday.app.data.local.entities.toEntity
import com.notaday.app.domain.model.Attachment
import com.notaday.app.domain.repository.AttachmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttachmentRepositoryImpl @Inject constructor(
    private val attachmentDao: AttachmentDao
) : AttachmentRepository {

    override fun getAttachmentsByNoteId(noteId: Long): Flow<List<Attachment>> {
        return attachmentDao.getAttachmentsByNoteId(noteId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getAttachmentById(id: Long): Attachment? {
        return attachmentDao.getAttachmentById(id)?.toDomain()
    }

    override suspend fun insertAttachment(attachment: Attachment): Long {
        return attachmentDao.insertAttachment(attachment.toEntity())
    }

    override suspend fun insertAttachments(attachments: List<Attachment>) {
        attachmentDao.insertAttachments(attachments.map { it.toEntity() })
    }

    override suspend fun deleteAttachment(attachment: Attachment) {
        attachmentDao.deleteAttachment(attachment.toEntity())
    }

    override suspend fun deleteAttachmentById(id: Long) {
        attachmentDao.deleteAttachmentById(id)
    }

    override suspend fun getAttachmentCountForNote(noteId: Long): Int {
        return attachmentDao.getAttachmentCountForNote(noteId)
    }
}
