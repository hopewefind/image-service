package com.hopewefind.imageservice.application

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class UploadImageServiceTest {

    private val logger: Logger = LoggerFactory.getLogger(UploadImageServiceTest::class.java)

    @Test
    fun `simple test`() {
        logger.info("Running simple test")
        Assertions.assertThat(true).isTrue()
    }
 
}
