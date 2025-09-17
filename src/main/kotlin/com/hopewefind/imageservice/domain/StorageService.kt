package com.hopewefind.imageservice.domain

import org.springframework.http.codec.multipart.FilePart

interface StorageService {
    suspend fun store(filePart: FilePart): String
}
