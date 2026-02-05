package com.notaday.app.presentation.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notaday.app.domain.model.Note
import com.notaday.app.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _notesForDate = MutableStateFlow<List<Note>>(emptyList())
    val notesForDate: StateFlow<List<Note>> = _notesForDate.asStateFlow()

    private val _noteDates = MutableStateFlow<List<LocalDate>>(emptyList())
    val noteDates: StateFlow<List<LocalDate>> = _noteDates.asStateFlow()

    init {
        loadNotesForSelectedDate()
        loadAllNoteDates()
    }

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
        loadNotesForSelectedDate()
    }

    private fun loadNotesForSelectedDate() {
        viewModelScope.launch {
            noteRepository.getNotesByDate(_selectedDate.value).collect { notes ->
                _notesForDate.value = notes
            }
        }
    }

    private fun loadAllNoteDates() {
        viewModelScope.launch {
            noteRepository.getAllNoteDates().collect { dates ->
                _noteDates.value = dates
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }
}
