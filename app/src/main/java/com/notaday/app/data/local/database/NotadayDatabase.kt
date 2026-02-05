package com.notaday.app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.notaday.app.data.local.dao.AttachmentDao
import com.notaday.app.data.local.dao.NoteDao
import com.notaday.app.data.local.entities.AttachmentEntity
import com.notaday.app.data.local.entities.NoteEntity

@Database(
    entities = [NoteEntity::class, AttachmentEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class NotadayDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun attachmentDao(): AttachmentDao

    companion object {
        const val DATABASE_NAME = "notaday_db"
    }
}
