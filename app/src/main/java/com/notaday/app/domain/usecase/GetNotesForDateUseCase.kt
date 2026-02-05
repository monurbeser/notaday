package com.notaday.app.domain.usecase

import com.notaday.app.domain.model.Note
import com.notaday.app.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetNotesForDateUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(date: LocalDate): Flow<List<Note>> {
        return noteRepository.getNotesByDate(date)
    }
}
