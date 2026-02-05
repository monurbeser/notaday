package com.notaday.app.util

import android.content.Context
import android.content.Intent
import com.notaday.app.domain.model.Note
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShareHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun shareNote(note: Note) {
        val shareText = buildShareText(note)
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share note via")
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(shareIntent)
    }

    fun shareNotes(notes: List<Note>) {
        val shareText = notes.joinToString("\n\n---\n\n") { buildShareText(it) }
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share notes via")
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(shareIntent)
    }

    private fun buildShareText(note: Note): String {
        val builder = StringBuilder()

        builder.append("📅 ${note.date.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"))}\n\n")

        if (note.title.isNotBlank()) {
            builder.append("${note.title}\n")
        }

        if (note.content.isNotBlank()) {
            builder.append("${note.content}\n")
        }

        if (note.isTodo) {
            builder.append("\n")
            val checkbox = if (note.isCompleted == true) "☑" else "☐"
            builder.append("$checkbox Todo")

            note.priority?.let {
                builder.append(" | Priority: ${it.name}")
            }

            note.deadline?.let {
                builder.append("\nDeadline: ${it.format(DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm"))}")
            }
        }

        if (note.attachments.isNotEmpty()) {
            builder.append("\n📎 ${note.attachments.size} attachment(s)")
        }

        return builder.toString()
    }
}
