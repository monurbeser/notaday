package com.notaday.app.presentation.notedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notaday.app.data.local.entities.FileType
import com.notaday.app.data.local.entities.Priority
import com.notaday.app.domain.model.Attachment
import com.notaday.app.domain.model.Note
import com.notaday.app.domain.repository.AttachmentRepository
import com.notaday.app.domain.usecase.SaveNoteUseCase
import com.notaday.app.domain.usecase.ToggleTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val saveNoteUseCase: SaveNoteUseCase,
    private val toggleTodoUseCase: ToggleTodoUseCase,
    private val attachmentRepository: AttachmentRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteId: Long = savedStateHandle.get<String>("noteId")?.toLongOrNull() ?: 0L

    private val _uiState = MutableStateFlow(NoteDetailUiState())
    val uiState: StateFlow<NoteDetailUiState> = _uiState.asStateFlow()

    init {
        if (noteId > 0) {
            loadNote(noteId)
        }
    }

    private fun loadNote(id: Long) {
        viewModelScope.launch {
            // Load note logic here
        }
    }

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun updateContent(content: String) {
        _uiState.value = _uiState.value.copy(content = content)
    }

    fun toggleTodo(isTodo: Boolean) {
        _uiState.value = _uiState.value.copy(isTodo = isTodo)
    }

    fun updateDeadline(deadline: LocalDateTime?) {
        _uiState.value = _uiState.value.copy(deadline = deadline)
    }

    fun updateReminder(reminderTime: LocalDateTime?) {
        _uiState.value = _uiState.value.copy(reminderTime = reminderTime)
    }

    fun updatePriority(priority: Priority?) {
        _uiState.value = _uiState.value.copy(priority = priority)
    }

    fun toggleCompleted() {
        _uiState.value = _uiState.value.copy(
            isCompleted = !(_uiState.value.isCompleted ?: false)
        )
    }

    fun addAttachment(filePath: String, fileType: FileType, fileName: String) {
        val newAttachment = Attachment(
            noteId = noteId,
            filePath = filePath,
            fileType = fileType,
            fileName = fileName,
            createdAt = LocalDateTime.now()
        )
        _uiState.value = _uiState.value.copy(
            attachments = _uiState.value.attachments + newAttachment
        )
    }

    fun removeAttachment(attachment: Attachment) {
        viewModelScope.launch {
            if (attachment.id > 0) {
                attachmentRepository.deleteAttachmentById(attachment.id)
            }
            _uiState.value = _uiState.value.copy(
                attachments = _uiState.value.attachments - attachment
            )
        }
    }

    fun saveNote(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            if (state.title.isBlank() && state.content.isBlank()) {
                return@launch
            }

            val note = Note(
                id = noteId,
                title = state.title,
                content = state.content,
                date = state.date,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                isTodo = state.isTodo,
                isCompleted = if (state.isTodo) state.isCompleted else null,
                deadline = if (state.isTodo) state.deadline else null,
                reminderTime = if (state.isTodo) state.reminderTime else null,
                priority = if (state.isTodo) state.priority else null,
                attachments = state.attachments
            )

            saveNoteUseCase(note)
            onSuccess()
        }
    }
}

data class NoteDetailUiState(
    val title: String = "",
    val content: String = "",
    val date: LocalDate = LocalDate.now(),
    val isTodo: Boolean = false,
    val isCompleted: Boolean? = null,
    val deadline: LocalDateTime? = null,
    val reminderTime: LocalDateTime? = null,
    val priority: Priority? = null,
    val attachments: List<Attachment> = emptyList()
)
