package com.notaday.app.data.repository

import com.notaday.app.data.local.dao.AttachmentDao
import com.notaday.app.data.local.dao.NoteDao
import com.notaday.app.data.local.entities.Priority
import com.notaday.app.data.local.entities.toDomain
import com.notaday.app.data.local.entities.toEntity
import com.notaday.app.domain.model.Note
import com.notaday.app.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val attachmentDao: AttachmentDao
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { notes ->
            notes.map { note ->
                val attachments = attachmentDao.getAttachmentsByNoteId(note.id).first()
                note.toDomain(attachments.map { it.toDomain() })
            }
        }
    }

    override fun getNotesByDate(date: LocalDate): Flow<List<Note>> {
        return noteDao.getNotesByDate(date).map { notes ->
            notes.map { note ->
                val attachments = attachmentDao.getAttachmentsByNoteId(note.id).first()
                note.toDomain(attachments.map { it.toDomain() })
            }
        }
    }

    override suspend fun getNoteById(id: Long): Note? {
        val noteEntity = noteDao.getNoteById(id) ?: return null
        val attachments = attachmentDao.getAttachmentsByNoteId(id).first()
        return noteEntity.toDomain(attachments.map { it.toDomain() })
    }

    override fun getNoteByIdFlow(id: Long): Flow<Note?> {
        return noteDao.getNoteByIdFlow(id).map { note ->
            note?.let {
                val attachments = attachmentDao.getAttachmentsByNoteId(id).first()
                it.toDomain(attachments.map { attachment -> attachment.toDomain() })
            }
        }
    }

    override fun getAllTodos(): Flow<List<Note>> {
        return noteDao.getAllTodos().map { notes ->
            notes.map { note ->
                val attachments = attachmentDao.getAttachmentsByNoteId(note.id).first()
                note.toDomain(attachments.map { it.toDomain() })
            }
        }
    }

    override fun getIncompleteTodos(): Flow<List<Note>> {
        return noteDao.getIncompleteTodos().map { notes ->
            notes.map { note ->
                val attachments = attachmentDao.getAttachmentsByNoteId(note.id).first()
                note.toDomain(attachments.map { it.toDomain() })
            }
        }
    }

    override fun getTodosByPriority(priority: Priority): Flow<List<Note>> {
        return noteDao.getTodosByPriority(priority).map { notes ->
            notes.map { note ->
                val attachments = attachmentDao.getAttachmentsByNoteId(note.id).first()
                note.toDomain(attachments.map { it.toDomain() })
            }
        }
    }

    override fun searchNotes(query: String): Flow<List<Note>> {
        return noteDao.searchNotes(query).map { notes ->
            notes.map { note ->
                val attachments = attachmentDao.getAttachmentsByNoteId(note.id).first()
                note.toDomain(attachments.map { it.toDomain() })
            }
        }
    }

    override fun getNotesInDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<Note>> {
        return noteDao.getNotesInDateRange(startDate, endDate).map { notes ->
            notes.map { note ->
                val attachments = attachmentDao.getAttachmentsByNoteId(note.id).first()
                note.toDomain(attachments.map { it.toDomain() })
            }
        }
    }

    override fun getAllNoteDates(): Flow<List<LocalDate>> {
        return noteDao.getAllNoteDates()
    }

    override suspend fun insertNote(note: Note): Long {
        val noteId = noteDao.insertNote(note.toEntity())
        note.attachments.forEach { attachment ->
            attachmentDao.insertAttachment(attachment.copy(noteId = noteId).toEntity())
        }
        return noteId
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note.toEntity())
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note.toEntity())
    }

    override suspend fun deleteNoteById(id: Long) {
        noteDao.deleteNoteById(id)
    }
}
