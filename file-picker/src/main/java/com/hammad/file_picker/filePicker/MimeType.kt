package com.hammad.file_picker.filePicker

import androidx.annotation.Keep

/**
 * It contains Intent MIME Type
 */
@Keep
enum class MimeType(val value: String) {
    ALL("*/*"),

    //IMAGE
    ALL_IMAGE("image/*"),
    IMAGE_PNG("image/png"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_JPG("image/jpg"),
    IMAGE_GIF("image/gif"),
    IMAGE_WEBP("image/webp"),

    //AUDIO
    ALL_AUDIO("audio/*"),
    AUDIO_AAC("audio/aac"),
    AUDIO_MPEG("audio/mpeg"),
    AUDIO_OGG("audio/ogg"),
    AUDIO_WEBM("audio/webm"),
    AUDIO_3GPP("audio/3gpp"),

    //DOCUMENT
    ALL_DOCS("application/*"),
    DOC_WORD("application/msword"),
    DOC_GZIP("application/gzip"),
    DOC_JSON("application/json"),
    DOC_OGG("application/ogg"),
    DOC_PDF("application/pdf"),
    DOC_ZIP("application/zip"),

    //TEXT
    ALL_TEXT("text/*"),
    TEXT_HTML("text/html"),
    TEXT_PLAIN("text/plain"),
    TEXT_CSV("text/csv"),

    //VIDEO
    ALL_VIDEO("video/*"),
    VIDEO_MP4("video/mp4"),
    VIDEO_MPEG("video/mpeg"),
    VIDEO_OGG("video/ogg"),
    VIDEO_MP2T("video/mp2t"),
    VIDEO_WEBM("video/webm"),
    VIDEO_3GPP("video/3gpp"),

    //FONT
    ALL_FONT("font/*"),
    FONT_WOFF("font/woff"),
    FONT_TTF("font/ttf"),
    FONT_OTF("font/otf")

}