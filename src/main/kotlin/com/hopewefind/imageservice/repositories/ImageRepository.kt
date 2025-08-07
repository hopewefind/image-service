package com.hopewefind.imageservice.repositories

import com.hopewefind.imageservice.repositories.entities.ImageEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository : ReactiveCrudRepository<ImageEntity, Long>