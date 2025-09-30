package com.hopewefind.imageservice.domain

import java.time.Instant

data class Image(
    val id: ImageId? = null,
    val uploaderId: UserId,
    val fileName: String,
    val objectKey: String,
    val contentType: String,
    val sizeBytes: Long,
    val uploadedAt: Instant? = null
) {
    init {
        require(fileName.isNotBlank()) { "fileName must not be blank" }
        require(objectKey.isNotBlank()) { "objectKey must not be blank" }
        require(contentType.isNotBlank()) { "contentType must not be blank" }
        require(sizeBytes >= 0) { "sizeBytes must be >= 0" }
    }
}
