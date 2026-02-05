package com.notaday.app.data.local.database

import androidx.room.TypeConverter
import com.notaday.app.data.local.entities.FileType
import com.notaday.app.data.local.entities.Priority
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {
    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(dateFormatter)
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it, dateFormatter) }
    }

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.format(dateTimeFormatter)
    }

    @TypeConverter
    fun toLocalDateTime(dateTimeString: String?): LocalDateTime? {
        return dateTimeString?.let { LocalDateTime.parse(it, dateTimeFormatter) }
    }

    @TypeConverter
    fun fromPriority(priority: Priority?): String? {
        return priority?.name
    }

    @TypeConverter
    fun toPriority(priorityString: String?): Priority? {
        return priorityString?.let { Priority.valueOf(it) }
    }

    @TypeConverter
    fun fromFileType(fileType: FileType): String {
        return fileType.name
    }

    @TypeConverter
    fun toFileType(fileTypeString: String): FileType {
        return FileType.valueOf(fileTypeString)
    }
}
