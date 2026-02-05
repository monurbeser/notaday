package com.notaday.app.domain.usecase

import com.notaday.app.domain.model.Note
import com.notaday.app.domain.repository.NoteRepository
import java.time.LocalDateTime
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note: Note): Long {
        val updatedNote = if (note.id == 0L) {
            note.copy(
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        } else {
            note.copy(updatedAt = LocalDateTime.now())
        }

        return if (note.id == 0L) {
            noteRepository.insertNote(updatedNote)
        } else {
            noteRepository.updateNote(updatedNote)
            note.id
        }
    }
}
