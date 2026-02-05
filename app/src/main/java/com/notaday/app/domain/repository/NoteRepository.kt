package com.notaday.app.domain.repository

import com.notaday.app.data.local.entities.Priority
import com.notaday.app.domain.model.Note
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    fun getNotesByDate(date: LocalDate): Flow<List<Note>>
    suspend fun getNoteById(id: Long): Note?
    fun getNoteByIdFlow(id: Long): Flow<Note?>
    fun getAllTodos(): Flow<List<Note>>
    fun getIncompleteTodos(): Flow<List<Note>>
    fun getTodosByPriority(priority: Priority): Flow<List<Note>>
    fun searchNotes(query: String): Flow<List<Note>>
    fun getNotesInDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<Note>>
    fun getAllNoteDates(): Flow<List<LocalDate>>
    suspend fun insertNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun deleteNoteById(id: Long)
}
