package com.hammad.file_picker.filePicker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class FilePickerContent(private val context: Context, private val fileConfig: FilePicker.Config) :
    ActivityResultContract<List<String>, List<UriData>>() {
    override fun createIntent(context: Context, input: List<String>): Intent {
        return Intent(Intent.ACTION_GET_CONTENT)
            .addCategory(Intent.CATEGORY_OPENABLE)
            .setType("*/*")
            .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, fileConfig.allowMultiple)
            .putExtra(Intent.EXTRA_MIME_TYPES, input.toTypedArray())
    }

    override fun parseResult(resultCode: Int, intent: Intent?): List<UriData> {
        if (intent == null || resultCode != Activity.RESULT_OK) {
            return emptyList()
        }
        return getClipDataUris(intent)
    }

    private fun getClipDataUris(intent: Intent): List<UriData> {
        val resultList: MutableList<UriData> = ArrayList()
        if (intent.data != null)
            resultList.add(
                UriData(
                    intent.data!!,
                    FileUtils.getMimeType(context, intent.data!!).orEmpty()
                )
            )

        val clipData = intent.clipData
        if (clipData == null && resultList.isEmpty()) {
            return emptyList()
        } else if (clipData != null)
            repeat(clipData.itemCount) {
                val uri = clipData.getItemAt(it).uri
                resultList.add(
                    UriData(
                        uri,
                        getMimeType(context, uri).orEmpty()
                    )
                )
            }
        return resultList
    }

    private fun getMimeType(context: Context, uri: Uri): String? {
        return context.contentResolver.getType(uri)
    }
}
