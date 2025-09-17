package com.hopewefind.imageservice.infrastructure.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("image")
data class ImageEntity(

    @Id
    val id: Long? = null,

    @Column("file_name")
    val fileName: String,

    val path: String
)
