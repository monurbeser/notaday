package com.notaday.app.data.local.dao

import androidx.room.*
import com.notaday.app.data.local.entities.NoteEntity
import com.notaday.app.data.local.entities.Priority
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY date DESC, createdAt DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE date = :date ORDER BY createdAt DESC")
    fun getNotesByDate(date: LocalDate): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Long): NoteEntity?

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteByIdFlow(id: Long): Flow<NoteEntity?>

    @Query("SELECT * FROM notes WHERE isTodo = 1 ORDER BY deadline ASC, priority DESC")
    fun getAllTodos(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE isTodo = 1 AND isCompleted = 0 ORDER BY deadline ASC, priority DESC")
    fun getIncompleteTodos(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE isTodo = 1 AND priority = :priority ORDER BY deadline ASC")
    fun getTodosByPriority(priority: Priority): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' ORDER BY date DESC")
    fun searchNotes(query: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC, createdAt DESC")
    fun getNotesInDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<NoteEntity>>

    @Query("SELECT DISTINCT date FROM notes ORDER BY date DESC")
    fun getAllNoteDates(): Flow<List<LocalDate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteNoteById(id: Long)

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()
}
