# Ktor Client Mobile Admin System

This project implements a Ktor Client for integrating with a mock API to simulate a **Mobile Network Admin System**. The client interacts with the JSONPlaceholder API (https://jsonplaceholder.typicode.com/), treating its "users" resource as subscribers of a mobile network.

## Integrated Service

**JSONPlaceholder** is a free fake API for testing. In this project, the `/users` endpoint is used to represent mobile network subscribers, with fields like `name`, `username` (as phone number), and `email`.

## Endpoints Used

The following endpoints are implemented to manage subscribers:

1. **GET /users**
    - Retrieves a list of subscribers.
    - Optional query parameter: `company.name` to filter by company.
    - Example: `GET /users?company.name=Romaguera-Crona`

2. **GET /users/{id}**
    - Retrieves a specific subscriber by ID.
    - Example: `GET /users/1`

3. **POST /users**
    - Creates a new subscriber.
    - Body: JSON object with `name`, `username` (phone number), and `email`.
    - Example: `POST /users` with `{"name": "John Doe", "username": "+380991234567", "email": "john.doe@example.com"}`

4. **PUT /users/{id}**
    - Updates a subscriber by ID.
    - Body: JSON object with `name`, `username`, and `email`.
    - Example: `PUT /users/1` with `{"name": "Jane Doe", "username": "+380991234568", "email": "jane.doe@example.com"}`

5. **PATCH /users/{id}**
    - Partially updates a subscriber (e.g., email).
    - Body: JSON object with fields to update.
    - Example: `PATCH /users/1` with `{"email": "new.email@example.com"}`

6. **DELETE /users/{id}**
    - Deletes a subscriber by ID.
    - Example: `DELETE /users/1`

## Project Structure

- **Main.kt**: Contains the `MobileAdminClient` class with Ktor Client configuration and methods for each endpoint.
- **Subscriber.kt**: Data class for serializing/deserializing subscriber data.
- **build.gradle.kts**: Gradle configuration with Ktor and Kotlin Serialization dependencies.

## Setup and Running

1. Clone the repository: `git clone <repository-url>`
2. Ensure JDK 11+ and Gradle are installed.
3. Run the project: `gradle run`
4. The client executes example requests and prints responses to the console.

## Dependencies

- Ktor Client (Core, CIO, Content Negotiation)
- Kotlinx Serialization JSON
- Kotlin 1.9.22

## Notes

- The client is configured with a base URL (`https://jsonplaceholder.typicode.com/`) and JSON content type.
- A custom header (`X-Admin-Auth: MobileAdminToken`) is included in all requests to simulate admin authentication.
- JSON serialization is handled using `kotlinx-serialization`.
- The CIO engine is used for the Ktor Client.

## GitHub Repository

The source code is available at: `<Insert your GitHub repository URL here>`