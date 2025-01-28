package com.hopewefind.sun.endpoints

import com.hopewefind.sun.services.ImageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.net.URI

@RestController
@RequestMapping("/api/image")
class ImageController(private val imageService: ImageService) {

    private val logger: Logger = LoggerFactory.getLogger(ImageController::class.java)

    @PostMapping("/upload", consumes = [MULTIPART_FORM_DATA_VALUE])
    fun uploadImage(@RequestPart("image") image: Mono<FilePart>): Mono<ResponseEntity<Map<String, String>>> {
        logger.debug("Received request to upload image")
        return image.flatMap { filePart ->
            imageService.saveFile(filePart)
        }.map { imageEntity ->
            val location = "/api/image/${imageEntity.id}"
            ResponseEntity.created(URI.create(location))
                .body(
                    mapOf(
                        "id" to imageEntity.id.toString(),
                        "fileName" to imageEntity.fileName,
                        "path" to imageEntity.path
                    )
                )
        }
    }

    @GetMapping("/ping")
    fun ping(): ResponseEntity<String> {
        return ResponseEntity.ok("pong")
    }

}