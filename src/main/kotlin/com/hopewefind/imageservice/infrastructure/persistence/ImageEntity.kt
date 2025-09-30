package com.hopewefind.imageservice.infrastructure.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("image")
data class ImageEntity(

    @Id
    val id: Long? = null,

    @Column("file_name")
    val fileName: String,

    @Column("object_key")
    val objectKey: String,

    @Column("content_type")
    val contentType: String,

    @Column("size_bytes")
    val sizeBytes: Long,

    @Column("uploaded_at")
    val uploadedAt: Instant? = null
)
