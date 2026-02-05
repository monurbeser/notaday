package com.notaday.app.util

import android.content.Context
import androidx.work.*
import com.notaday.app.domain.model.Note
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val workManager = WorkManager.getInstance(context)

    fun scheduleNotification(note: Note) {
        note.reminderTime?.let { reminderTime ->
            val now = LocalDateTime.now()
            val delay = Duration.between(now, reminderTime)

            if (delay.isNegative || delay.isZero) {
                return
            }

            val data = Data.Builder()
                .putLong(NotificationWorker.KEY_NOTE_ID, note.id)
                .build()

            val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInitialDelay(delay.toMinutes(), TimeUnit.MINUTES)
                .setInputData(data)
                .addTag(getWorkTag(note.id))
                .build()

            workManager.enqueueUniqueWork(
                getWorkName(note.id),
                ExistingWorkPolicy.REPLACE,
                workRequest
            )
        }
    }

    fun cancelNotification(noteId: Long) {
        workManager.cancelUniqueWork(getWorkName(noteId))
    }

    fun cancelAllNotifications() {
        workManager.cancelAllWork()
    }

    private fun getWorkName(noteId: Long): String = "notification_$noteId"
    private fun getWorkTag(noteId: Long): String = "note_reminder_$noteId"
}
