package com.hammad.file_picker.filePicker

import android.content.Context
import android.net.Uri

object FileUtils {

    fun getMimeType(context: Context, uri: Uri): String? {
        return context.contentResolver.getType(uri)
    }
}