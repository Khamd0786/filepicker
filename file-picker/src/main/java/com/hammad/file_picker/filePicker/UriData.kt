package com.hammad.file_picker.filePicker

import android.net.Uri

/**
 * [UriData] contains some [Uri] related details
 */
data class UriData(
    /**
     * [Uri] of files which selected from FileManager
     */
    var uri: Uri,
    /**
     * [MimeType][String] of the following [Uri] which selected from FileManager
     */
    var mimeType: String
)