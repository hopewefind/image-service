package com.hopewefind.imageservice.application

import com.hopewefind.imageservice.domain.Image
import com.hopewefind.imageservice.domain.ImageRepository
import com.hopewefind.imageservice.domain.StorageService
import com.hopewefind.imageservice.domain.UserId
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class UploadImageService(
    private val imageRepository: ImageRepository,
    private val storageService: StorageService,
) {
    suspend fun upload(filePart: FilePart, uploaderId: UserId): Image {
        val storedPath = storageService.store(filePart)

        val image = Image(
            uploaderId = uploaderId,
            fileName = filePart.filename(),
            path = storedPath,
            uploadedAt = Instant.now()
        )

        return imageRepository.save(image)
    }
}
