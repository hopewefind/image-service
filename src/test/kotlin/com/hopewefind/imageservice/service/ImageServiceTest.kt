package com.hopewefind.imageservice.service

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ImageServiceTest {

    private val logger: Logger = LoggerFactory.getLogger(ImageServiceTest::class.java)

    @Test
    fun `simple test`() {
        logger.info("Running simple test")
        Assertions.assertThat(true).isTrue()
    }

}