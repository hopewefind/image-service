package com.hopewefind.imageservice.infrastructure.persistence

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageR2dbcRepository : ReactiveCrudRepository<ImageEntity, Long>
