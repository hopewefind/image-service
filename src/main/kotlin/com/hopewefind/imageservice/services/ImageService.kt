package com.hopewefind.imageservice.services

import com.hopewefind.imageservice.repositories.ImageRepository
import com.hopewefind.imageservice.repositories.entities.ImageEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Service
class ImageService(
    private val imageRepository: ImageRepository,
    @Value("\${image.storage-path}") private val storagePath: String,
) {

    private val logger: Logger = LoggerFactory.getLogger(ImageService::class.java)

    fun saveFile(filePart: FilePart): Mono<ImageEntity> {
        val uploadsPath = Paths.get(storagePath)
        val resolvedPath = uploadsPath.resolve(filePart.filename())
        return Mono.fromCallable { createDirIfNotExists(uploadsPath) }
            .subscribeOn(Schedulers.boundedElastic())
            .then(filePart.transferTo(resolvedPath))
            .then(imageRepository.save(ImageEntity(fileName = filePart.filename(), path = resolvedPath.toString())))
            .doOnSuccess { logger.debug("File {} was successfully saved to {}", filePart.filename(), resolvedPath) }
            .mapNotNull { it }
    }

    private fun createDirIfNotExists(uploadsPath: Path) {
        if (!Files.exists(uploadsPath)) {
            Files.createDirectories(uploadsPath)
        }
    }
}
