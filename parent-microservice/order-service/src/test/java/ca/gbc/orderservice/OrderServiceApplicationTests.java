package ca.gbc.orderservice;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;

@Import(TestcontainersConfiguration.class)
// Tells Spring Boot to look for a main configuration class (@SpringBootApplication)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {

	// TestContainers annotation for PostgreSQL
	@ServiceConnection
	static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
			.withDatabaseName("testdb")
			.withUsername("testuser")
			.withPassword("testpass");

	@LocalServerPort
	private Integer port;

	// Setup RestAssured base URI and port
	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	// Static block to start the PostgreSQL container
	static {
		postgreSQLContainer.start();
	}

	@Test
	void placeOrderTest() {
		String requestBody = """
                {
                    "skuCode": "SKU001",
                    "price": 100.50,
                    "quantity": 2
                }
                """;

		// Test POST request to place an order
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/order")
				.then()
				.log().all()
				.statusCode(201)
				.body("message", Matchers.equalTo("Order placed successfully"))
				.body("order.id", Matchers.notNullValue())
				.body("order.skuCode", Matchers.equalTo("SKU001"))
				.body("order.price", Matchers.equalTo(100.50F)) // Explicit float comparison // Use when calculating money
				.body("order.quantity", Matchers.equalTo(2));
	}
}
