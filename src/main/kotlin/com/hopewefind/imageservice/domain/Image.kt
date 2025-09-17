package com.hopewefind.imageservice.domain

import java.time.Instant

data class Image(
    val id: ImageId? = null,
    val uploaderId: UserId,
    val fileName: String,
    val path: String,
    val uploadedAt: Instant
)
