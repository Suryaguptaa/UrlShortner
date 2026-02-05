# 🔗 URL Shortener

A production-ready URL shortening service built with Spring Boot that converts long URLs into compact 6-character codes using Base62 encoding. This service guarantees collision-free short codes by deriving them directly from database IDs.

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.x** (Spring Web, Spring Data JPA)
- **Maven** - Build and dependency management
- **H2 Database** - In-memory database for development
- **Hibernate** - ORM implementation

## ✨ Features

- **Base62 Encoding**: Converts numeric database IDs into alphanumeric short codes using `[0-9, a-z, A-Z]`
- **Collision-Free**: Mathematical guarantee of unique short codes (no random generation)
- **HTTP 302 Redirects**: Seamless forwarding from short URL to original destination
- **Transactional Integrity**: Double-save strategy ensures no orphaned records
- **Error Handling**: Graceful 404 responses for non-existent short codes
- **Database Constraints**: Enforces unique short codes at the database level

## 🧠 How It Works

### The Algorithm

1. **Storage**: When a long URL is submitted, it's saved to the database. The database auto-generates a unique ID (e.g., `12345`)
2. **Encoding**: The numeric ID is converted to Base62 format:
   ```
   12345 (Base 10) → 3d7 (Base 62)
   ```
3. **Decoding**: When `/3d7` is accessed, the system decodes it back to ID `12345`, retrieves the original URL, and issues a 302 redirect

### Double-Save Strategy

The service uses a two-step transactional process:
1. First save: Persist URL to generate database ID
2. Encode: Convert ID to Base62 short code
3. Second save: Update record with the short code

This ensures data consistency and prevents records without short codes.

## 🏗️ Architecture

```
Client Request
     ↓
UrlController (API Layer)
     ↓
UrlService (Business Logic + Base62 Algorithm)
     ↓
UrlRepository (Data Access)
     ↓
H2 Database
```

### Project Structure

```
src/main/java/com/link/urlshortner/
├── controller/
│   └── UrlController.java          # REST endpoints
├── service/
│   └── UrlService.java             # Core logic and Base62 encoding
├── repository/
│   └── UrlRepository.java          # JPA repository interface
├── entity/
│   └── UrlMapping_entity.java      # Database entity
└── dto/
    └── UrlRequest.java             # Request payload DTO
```

## 📡 API Reference

### Shorten a URL

```http
POST /shorten
Content-Type: application/json
```

**Request Body:**
```json
{
    "originalUrl": "https://github.com/spring-projects/spring-boot"
}
```

**Response:**
```
3d7
```

### Redirect to Original URL

```http
GET /{shortCode}
```

**Example:**
```http
GET /3d7
```

**Response:**
- Status: `302 Found`
- Location header: Original URL
- Browser automatically follows redirect

### Error Handling

```http
GET /InvalidCode
```

**Response:**
- Status: `500 Internal Server Error` or `404 Not Found`
- Occurs when short code doesn't exist in database

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Installation

1. Clone the repository
```bash
git clone https://github.com/yourusername/url-shortener.git
cd url-shortener
```

2. Configure database (optional)

The project uses H2 in-memory database by default. Configuration in `src/main/resources/application.properties`:

```properties
spring.application.name=UrlShortener

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.jpa.hibernate.ddl-auto=update
```

3. Build and run
```bash
mvn clean install
mvn spring-boot:run
```

The server starts at `http://localhost:8080`

## 🧪 Testing with Postman

### Test 1: Create Short URL

1. Create a POST request to `http://localhost:8080/shorten`
2. Set header: `Content-Type: application/json`
3. Body (raw JSON):
```json
{
    "originalUrl": "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
}
```
4. Send request
5. Copy the short code from response (e.g., `b8Z`)

### Test 2: Verify Redirect

1. Create a GET request to `http://localhost:8080/b8Z`
2. Send request
3. Postman will show `200 OK` (it follows the redirect automatically)
4. To see the actual `302` status, disable "Automatically follow redirects" in Postman Settings

### Test 3: Invalid Short Code

1. GET request to `http://localhost:8080/InvalidCode123`
2. Expected: `500 Internal Server Error` or `404 Not Found`

## 🔍 Database Access

Access the H2 console at `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

Query example:
```sql
SELECT * FROM url_mapping;
```

## 🔮 Future Enhancements

- [ ] Custom short code support (user-defined aliases)
- [ ] URL expiration and TTL
- [ ] Click analytics and tracking
- [ ] QR code generation for short URLs
- [ ] Rate limiting and API authentication
- [ ] Redis caching for high-traffic links
- [ ] Migration to PostgreSQL/MySQL for production
- [ ] REST API documentation with Swagger/OpenAPI

## 📄 License

This project is licensed under the MIT License.