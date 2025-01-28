package com.hopewefind.sun.repositories

import com.hopewefind.sun.repositories.entities.ImageEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository : ReactiveCrudRepository<ImageEntity, Long>