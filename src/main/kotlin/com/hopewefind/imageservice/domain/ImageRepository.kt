package com.hopewefind.imageservice.domain

interface ImageRepository {
    suspend fun save(image: Image): Image
    suspend fun findById(id: ImageId): Image?
}
