package com.hopewefind.imageservice.infrastructure.persistence

import com.hopewefind.imageservice.domain.Image
import com.hopewefind.imageservice.domain.ImageId
import com.hopewefind.imageservice.domain.ImageRepository
import com.hopewefind.imageservice.domain.UserId
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class SpringImageRepositoryAdapter(private val imageJpaRepository: ImageJpaRepository) : ImageRepository {
    override suspend fun save(image: Image): Image =
        imageJpaRepository.save(image.toEntity()).awaitSingle().toDomain()

    override suspend fun findById(id: ImageId): Image? =
        imageJpaRepository.findById { id.value }.awaitFirstOrNull()?.toDomain()

    fun ImageEntity.toDomain(): Image = Image(
        id = ImageId(this.id!!),
        uploaderId = UserId(0),
        fileName = this.fileName,
        path = this.path,
        uploadedAt = Instant.now()
    )

    fun Image.toEntity(): ImageEntity = ImageEntity(
        id = this.id?.value,
        fileName = this.fileName,
        path = this.path
    )
}
