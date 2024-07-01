package com.aliumujib.model

import android.net.Uri

sealed class DownloadEvent(open val downloadId: Long) {
    data class Progress(override val downloadId: Long, val progress: Int) : DownloadEvent(downloadId)
    data class Complete(override val downloadId: Long, val uri: Uri, val breed: Breed? = null) : DownloadEvent(downloadId)
    data class Failure(override val downloadId: Long, val reason: String) : DownloadEvent(downloadId)
    data class Cancellation(override val downloadId: Long) : DownloadEvent(downloadId)
}