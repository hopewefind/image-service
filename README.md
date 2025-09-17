# Image Service

A reactive image management service built with Spring Boot and Kotlin that provides REST APIs for uploading and managing images.

## Overview

This service is designed with clean architecture principles, featuring:

- **Reactive Programming**: Built with Spring WebFlux and R2DBC for non-blocking operations
- **Domain-Driven Design**: Clear separation between domain logic, application services, and infrastructure
- **File Storage**: File system-based image storage with configurable paths
- **Database**: PostgreSQL with R2DBC for reactive database operations
- **Testing**: Comprehensive unit and integration tests with Testcontainers

## Technology Stack

- **Language**: Kotlin 1.9.25
- **Framework**: Spring Boot 3.5.5 with WebFlux
- **Database**: PostgreSQL with R2DBC
- **Build Tool**: Maven
- **Java Version**: 21
- **Testing**: JUnit 5, AssertJ, Testcontainers
- **Code Quality**: Detekt static analysis

## Project Structure

```
src/
├── main/kotlin/com/hopewefind/imageservice/
│   ├── domain/                 # Core business logic
│   │   ├── Image.kt           # Main domain entity
│   │   ├── ImageId.kt         # Value objects
│   │   ├── UserId.kt
│   │   ├── ImageRepository.kt  # Repository interface
│   │   └── StorageService.kt   # Storage service interface
│   ├── application/           # Use cases and business workflows
│   │   └── UploadImageService.kt
│   ├── endpoints/             # REST controllers and DTOs
│   │   ├── ImageController.kt
│   │   └── ImageResponseDto.kt
│   └── infrastructure/        # Technical implementations
│       ├── persistence/       # Database layer
│       └── storage/          # File storage implementations
├── test/kotlin/              # Unit tests
└── it/kotlin/               # Integration tests
```

## Prerequisites

### Local Development
- **Java 21** (Eclipse Temurin recommended)
- **Maven 3.9+**
- **Docker & Docker Compose** (for database and containerized deployment)

### Database
- **PostgreSQL 17.2+** (can be run via Docker Compose)

## Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd image-service
```

### 2. Start Database (Docker Compose)
```bash
docker-compose up database -d
```

This starts PostgreSQL on `localhost:5432` with default configuration from docker-compose.yml

### 3. Run the Application

#### Option A: Using Maven
```bash
mvn spring-boot:run
```

#### Option B: Using Docker Compose (Full Stack)
```bash
docker-compose up
```

The application will be available at `http://localhost:8080`

### 4. Verify Installation
```bash
curl http://localhost:8080/api/image/ping
# Expected response: "pong"
```

## Development Commands

### Building and Testing
```bash
# Compile the project
mvn clean compile

# Run unit tests
mvn test

# Run integration tests
mvn failsafe:integration-test

# Run all tests and quality checks
mvn verify

# Package the application
mvn package
```

### Code Quality
```bash
# Run static analysis with Detekt
mvn verify
# Report generated in: reports/detekt.xml
```

## API Endpoints

### Upload Image
```http
POST /api/image/upload
Content-Type: multipart/form-data

Form Data:
- image: [image file]
```

**Response:**
```json
{
  "id": 1,
  "fileName": "example.jpg",
  "path": "test-uploads/generated-filename.jpg"
}
```

### Health Check
```http
GET /api/image/ping
```

**Response:**
```
pong
```

## Configuration

### Application Properties
Key configuration options in `application.yml`:

```yaml
image:
  storage-path: test-uploads  # Directory for storing uploaded images

spring:
  application:
    name: image-service
  r2dbc:
    url: r2dbc:postgresql://localhost:5432/your-database
    username: your-username
    password: your-password
```

### Environment Variables (Docker)
- `SPRING_R2DBC_URL`: Database connection URL
- `SPRING_R2DBC_USERNAME`: Database username
- `SPRING_R2DBC_PASSWORD`: Database password

## Database Schema

The service uses a simple schema with automatic initialization:

```sql
CREATE TABLE IF NOT EXISTS image (
    id BIGSERIAL NOT NULL,
    file_name CHARACTER VARYING,
    path CHARACTER VARYING
);
```

## Testing

### Running Tests

```bash
# Unit tests only
mvn test

# Integration tests only (requires Docker for Testcontainers)
mvn failsafe:integration-test

# All tests
mvn verify
```

### Test Structure
- **Unit Tests**: Test individual components with mocking
- **Integration Tests**: Full application context with Testcontainers PostgreSQL
- **Base Test Class**: `BaseIntegrationTest` provides common integration test setup

## Development Notes

### Reactive Programming
- Uses Kotlin coroutines with `suspend` functions
- Database operations are non-blocking with R2DBC
- File operations are handled synchronously (consider async file handling for production)

### Architecture Patterns
- **Hexagonal Architecture**: Domain core isolated from infrastructure
- **Repository Pattern**: Abstract data access with clean interfaces
- **Dependency Inversion**: High-level modules don't depend on low-level modules

### Storage
- Images are stored in the file system under the configured `storage-path`
- File names are generated to avoid conflicts
- Consider cloud storage integration for production deployments

## Deployment

### Docker
Build and run with Docker:

```bash
# Build image
docker build -t image-service .

# Run with database
docker-compose up
```

### Production Considerations
- Configure persistent volumes for image storage
- Set up proper database connection pooling
- Configure logging and monitoring
- Consider cloud storage for scalability
- Implement authentication and authorization
- Add rate limiting and validation

## Contributing

1. Follow the existing code style and architecture patterns
2. Write tests for new features
3. Run `mvn verify` before committing to ensure code quality
4. Use meaningful commit messages

## License

[Add your license information here]