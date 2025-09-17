package com.hopewefind.imageservice.infrastructure.storage

import com.hopewefind.imageservice.domain.StorageService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Component
class FileSystemStorageService(
    @Value("\${image.storage-path}") private val storagePath: String
) : StorageService {
    override suspend fun store(filePart: FilePart): String {
        val uploadsPath = Paths.get(storagePath)
        val resolvedPath = uploadsPath.resolve(filePart.filename())

        withContext(Dispatchers.IO) {
            createDirIfNotExists(uploadsPath)
            filePart.transferTo(resolvedPath).awaitFirstOrNull()
        }

        return resolvedPath.toString()
    }

    private fun createDirIfNotExists(uploadsPath: Path) {
        if (!Files.exists(uploadsPath)) {
            Files.createDirectories(uploadsPath)
        }
    }
}
