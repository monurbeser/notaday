package com.notaday.app.domain.model

import com.notaday.app.data.local.entities.FileType
import java.time.LocalDateTime

data class Attachment(
    val id: Long = 0,
    val noteId: Long,
    val filePath: String,
    val fileType: FileType,
    val fileName: String,
    val createdAt: LocalDateTime
)
