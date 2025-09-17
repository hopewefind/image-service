package com.hopewefind.imageservice.endpoints

import com.hopewefind.imageservice.BaseIntegrationTest
import com.hopewefind.imageservice.infrastructure.persistence.ImageJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.test.web.reactive.server.WebTestClient
import java.nio.file.Path

class ImageControllerIT : BaseIntegrationTest() {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var imageJpaRepository: ImageJpaRepository

    @TempDir
    private lateinit var tempDir: Path

    @BeforeEach
    fun setUp() {
        imageJpaRepository.deleteAll().block()
    }

    @AfterEach
    fun tearDown() {
        tempDir.toFile().deleteRecursively()
    }

    @Test
    fun `test file upload response`() {
        val resource = ClassPathResource("sample_image.jpg")

        webTestClient.post()
            .uri("/api/image/upload")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .bodyValue(
                MultipartBodyBuilder().apply {
                    part("image", resource)
                }.build()
            )
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.id").isNotEmpty
            .jsonPath("$.fileName").isEqualTo("sample_image.jpg")
            .jsonPath("$.path").isNotEmpty
    }

    @Test
    fun `test stored object in database`() {
        val resource = ClassPathResource("sample_image.jpg")

        webTestClient.post()
            .uri("/api/image/upload")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .bodyValue(
                MultipartBodyBuilder().apply {
                    part("image", resource)
                }.build()
            )
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.id").value<Int> { id ->
                val imageEntity = imageJpaRepository.findById(id.toLong()).block()
                assertThat(imageEntity).isNotNull
                assertThat(imageEntity?.fileName).isEqualTo("sample_image.jpg")
                assertThat(imageEntity?.path).contains("sample_image.jpg")
            }
    }
}
