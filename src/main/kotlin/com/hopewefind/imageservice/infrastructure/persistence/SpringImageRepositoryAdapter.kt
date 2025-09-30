package com.hopewefind.imageservice.infrastructure.persistence

import com.hopewefind.imageservice.domain.Image
import com.hopewefind.imageservice.domain.ImageId
import com.hopewefind.imageservice.domain.ImageRepository
import com.hopewefind.imageservice.domain.UserId
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component

@Component
class SpringImageRepositoryAdapter(private val imageRepository: ImageR2dbcRepository) : ImageRepository {
    override suspend fun save(image: Image): Image =
        imageRepository.save(image.toEntity()).awaitSingle().toDomain()

    override suspend fun findById(id: ImageId): Image? =
        imageRepository.findById(id.value).awaitFirstOrNull()?.toDomain()

    fun ImageEntity.toDomain(): Image = Image(
        id = ImageId(this.id!!),
        uploaderId = UserId(0),
        fileName = this.fileName,
        objectKey = this.objectKey,
        contentType = this.contentType,
        sizeBytes = this.sizeBytes,
        uploadedAt = this.uploadedAt
    )

    fun Image.toEntity(): ImageEntity = ImageEntity(
        id = this.id?.value,
        fileName = this.fileName,
        objectKey = this.objectKey,
        contentType = this.contentType,
        sizeBytes = this.sizeBytes,
        uploadedAt = this.uploadedAt
    )
}
