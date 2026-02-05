package com.notaday.app.domain.usecase

import com.notaday.app.domain.model.Note
import com.notaday.app.domain.repository.NoteRepository
import java.time.LocalDateTime
import javax.inject.Inject

class ToggleTodoUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note: Note, isTodo: Boolean): Note {
        val updatedNote = if (isTodo) {
            note.copy(
                isTodo = true,
                isCompleted = false,
                updatedAt = LocalDateTime.now()
            )
        } else {
            note.copy(
                isTodo = false,
                isCompleted = null,
                deadline = null,
                reminderTime = null,
                priority = null,
                updatedAt = LocalDateTime.now()
            )
        }

        noteRepository.updateNote(updatedNote)
        return updatedNote
    }
}
