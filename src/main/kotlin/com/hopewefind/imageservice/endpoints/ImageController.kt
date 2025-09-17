package com.hopewefind.imageservice.endpoints

import com.hopewefind.imageservice.application.UploadImageService
import com.hopewefind.imageservice.domain.UserId
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import java.net.URI

private const val FIXED_USER_ID = 123L

@RestController
@RequestMapping("/api/image")
class ImageController(private val uploadImageService: UploadImageService) {

    private val logger: Logger = LoggerFactory.getLogger(ImageController::class.java)

    @PostMapping("/upload", consumes = [MULTIPART_FORM_DATA_VALUE])
    suspend fun uploadImage(@RequestPart("image") image: FilePart): ResponseEntity<ImageResponseDto> {
        logger.debug("Received request to upload image")

        val uploaderId = UserId(FIXED_USER_ID)
        val savedImage = uploadImageService.upload(image, uploaderId)
        val location = URI.create("/api/image/${savedImage.id}")
        val response = ImageResponseDto(
            id = savedImage.id!!.value,
            fileName = savedImage.fileName,
            path = savedImage.path
        )
        return ResponseEntity.created(location).body(response)
    }

    @GetMapping("/ping")
    fun ping(): ResponseEntity<String> {
        return ResponseEntity.ok("pong")
    }
}
