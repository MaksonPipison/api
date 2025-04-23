import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.coroutines.runBlocking

@Serializable
data class Subscriber(
    val id: Int? = null,
    val name: String,
    val username: String, // Використовуємо як номер телефону
    val email: String,
    val company: Company? = null // Компанія абонента (для фільтрації)
)

@Serializable
data class Company(
    val name: String
)

class MobileAdminClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        defaultRequest {
            url("https://jsonplaceholder.typicode.com/")
            contentType(ContentType.Application.Json)
            header("X-Admin-Auth", "MobileAdminToken") // Кастомний заголовок
        }
    }

    suspend fun getSubscribers(companyName: String? = null): List<Subscriber> {
        return client.get("users") {
            companyName?.let { parameter("company.name", it) }
        }.body()
    }

    suspend fun getSubscriberById(id: Int): Subscriber {
        return client.get("users/$id").body()
    }

    suspend fun createSubscriber(subscriber: Subscriber): Subscriber {
        return client.post("users") {
            setBody(subscriber)
        }.body()
    }

    suspend fun updateSubscriber(id: Int, subscriber: Subscriber): Subscriber {
        return client.put("users/$id") {
            setBody(subscriber)
        }.body()
    }

    suspend fun patchSubscriberEmail(id: Int, email: String): Subscriber {
        return client.patch("users/$id") {
            setBody(mapOf("email" to email))
        }.body()
    }

    suspend fun deleteSubscriber(id: Int): HttpResponse {
        return client.delete("users/$id")
    }
}

fun main() = runBlocking {
    val client = MobileAdminClient()

    // GET: Отримати всіх абонентів
    println("GET all subscribers:")
    val subscribers = client.getSubscribers()
    println(subscribers.take(2))

    // GET: Отримати абонентів за назвою компанії
    println("\nGET subscribers by company name 'Romaguera-Crona':")
    val companySubscribers = client.getSubscribers(companyName = "Romaguera-Crona")
    println(companySubscribers)

    // GET: Отримати абонента за ID
    println("\nGET subscriber by ID=1:")
    val subscriber = client.getSubscriberById(1)
    println(subscriber)

    // POST: Створити нового абонента
    println("\nPOST new subscriber:")
    val newSubscriber = Subscriber(
        name = "John Doe",
        username = "+380991234567",
        email = "john.doe@example.com"
    )
    val createdSubscriber = client.createSubscriber(newSubscriber)
    println(createdSubscriber)

    // PUT: Оновити абонента
    println("\nPUT update subscriber ID=1:")
    val updatedSubscriber = Subscriber(
        name = "Jane Doe",
        username = "+380991234568",
        email = "jane.doe@example.com"
    )
    val putSubscriber = client.updateSubscriber(1, updatedSubscriber)
    println(putSubscriber)

    // PATCH: Оновити email абонента
    println("\nPATCH update subscriber email ID=1:")
    val patchedSubscriber = client.patchSubscriberEmail(1, "new.email@example.com")
    println(patchedSubscriber)

    // DELETE: Видалити абонента
    println("\nDELETE subscriber ID=1:")
    val deleteResponse = client.deleteSubscriber(1)
    println("Delete status: ${deleteResponse.status}")
}