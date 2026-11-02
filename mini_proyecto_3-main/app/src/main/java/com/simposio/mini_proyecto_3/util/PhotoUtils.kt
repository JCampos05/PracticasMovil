package com.simposio.mini_proyecto_3.util

import android.content.Context
import androidx.core.content.FileProvider
import android.net.Uri
import java.io.File
import java.io.IOException

object PhotoUtils {
    private const val FOLDER = "fotos"

    private fun createFile(context: Context): File {
        val folder = File(context.filesDir, FOLDER).apply { mkdirs() }
        return File(folder, "pic_${System.currentTimeMillis()}.jpg").apply { createNewFile() }
    }

    private fun fileUri(context: Context, file: File): Uri =
        FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)

    fun createCameraUri(context: Context): Uri =
        fileUri(context, createFile(context))

    fun copyFromGallery(context: Context, origin: Uri): Uri? {
        return try {
            val destiny = createFile(context)
            val input = context.contentResolver.openInputStream(origin) ?: return null

            input.use { inputStream ->
                destiny.outputStream().use { outputStream -> inputStream.copyTo(outputStream) }
            }

            fileUri(context, destiny)
        } catch (e: IOException) {
            null
        }
    }

    fun delete(context: Context, uri: Uri) {
        try {
            context.contentResolver.delete(uri, null, null)
        } catch (e: Exception) {
            // No se borra = problema de otro
        }
    }
}