package com.notaday.app.di

import android.content.Context
import androidx.room.Room
import com.notaday.app.data.local.dao.AttachmentDao
import com.notaday.app.data.local.dao.NoteDao
import com.notaday.app.data.local.database.NotadayDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNotadayDatabase(
        @ApplicationContext context: Context
    ): NotadayDatabase {
        return Room.databaseBuilder(
            context,
            NotadayDatabase::class.java,
            NotadayDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(database: NotadayDatabase): NoteDao {
        return database.noteDao()
    }

    @Provides
    @Singleton
    fun provideAttachmentDao(database: NotadayDatabase): AttachmentDao {
        return database.attachmentDao()
    }
}
