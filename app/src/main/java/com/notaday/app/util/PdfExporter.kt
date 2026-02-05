package com.notaday.app.util

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Environment
import com.notaday.app.domain.model.Note
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PdfExporter @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun exportToPdf(
        notes: List<Note>,
        includePhotos: Boolean = true,
        fileName: String = "notaday_export_${System.currentTimeMillis()}.pdf"
    ): File? {
        return try {
            val pdfDocument = PdfDocument()
            val pageWidth = 595 // A4 width in points
            val pageHeight = 842 // A4 height in points

            var pageNumber = 1
            var currentPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
            var currentPage = pdfDocument.startPage(currentPageInfo)
            var canvas = currentPage.canvas
            var yPosition = 50f

            val paint = android.graphics.Paint().apply {
                textSize = 12f
                color = android.graphics.Color.BLACK
            }

            val titlePaint = android.graphics.Paint().apply {
                textSize = 18f
                color = android.graphics.Color.BLACK
                isFakeBoldText = true
            }

            val headerPaint = android.graphics.Paint().apply {
                textSize = 14f
                color = android.graphics.Color.BLACK
                isFakeBoldText = true
            }

            canvas.drawText("Notaday Export", 50f, yPosition, titlePaint)
            yPosition += 30f
            canvas.drawText("Generated: ${LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))}", 50f, yPosition, paint)
            yPosition += 40f

            notes.forEach { note ->
                if (yPosition > pageHeight - 100) {
                    pdfDocument.finishPage(currentPage)
                    pageNumber++
                    currentPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
                    currentPage = pdfDocument.startPage(currentPageInfo)
                    canvas = currentPage.canvas
                    yPosition = 50f
                }

                canvas.drawText("${note.date.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))}", 50f, yPosition, headerPaint)
                yPosition += 25f

                if (note.title.isNotBlank()) {
                    canvas.drawText("Title: ${note.title}", 70f, yPosition, paint)
                    yPosition += 20f
                }

                if (note.content.isNotBlank()) {
                    val contentLines = note.content.chunked(80)
                    contentLines.forEach { line ->
                        if (yPosition > pageHeight - 100) {
                            pdfDocument.finishPage(currentPage)
                            pageNumber++
                            currentPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
                            currentPage = pdfDocument.startPage(currentPageInfo)
                            canvas = currentPage.canvas
                            yPosition = 50f
                        }
                        canvas.drawText(line, 70f, yPosition, paint)
                        yPosition += 20f
                    }
                }

                if (note.isTodo) {
                    val todoStatus = if (note.isCompleted == true) "✓ Completed" else "○ Incomplete"
                    canvas.drawText("Todo Status: $todoStatus", 70f, yPosition, paint)
                    yPosition += 20f

                    note.priority?.let {
                        canvas.drawText("Priority: ${it.name}", 70f, yPosition, paint)
                        yPosition += 20f
                    }

                    note.deadline?.let {
                        canvas.drawText("Deadline: ${it.format(DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm"))}", 70f, yPosition, paint)
                        yPosition += 20f
                    }
                }

                if (note.attachments.isNotEmpty()) {
                    canvas.drawText("Attachments: ${note.attachments.size} file(s)", 70f, yPosition, paint)
                    yPosition += 20f
                }

                yPosition += 30f
            }

            canvas.drawText("Page $pageNumber", pageWidth - 100f, pageHeight - 30f, paint)
            pdfDocument.finishPage(currentPage)

            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, fileName)
            pdfDocument.writeTo(FileOutputStream(file))
            pdfDocument.close()

            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
