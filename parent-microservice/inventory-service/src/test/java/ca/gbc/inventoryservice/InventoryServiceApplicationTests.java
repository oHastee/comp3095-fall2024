package ca.gbc.inventoryservice;

import ca.gbc.inventoryservice.repository.InventoryRepository;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

	@ServiceConnection
	static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine");

	@LocalServerPort
	private Integer port;

	@Autowired
	private InventoryRepository inventoryRepository;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		postgreSQLContainer.start();
	}

	@Test
	void contextLoads() {
		// Basic test to verify that the Spring context loads successfully.
	}

	@Test
	void testInventoryRepository() {
		boolean isStockAvailable = inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual("test_sku", 10);
		assertThat(isStockAvailable).isFalse(); // Assuming test data setup
	}

	@Test
	void isInStockEndpointTest() {
		RestAssured.given()
				.contentType("application/json")
				.queryParam("skuCode", "test_sku")
				.queryParam("quantity", 10)
				.when()
				.get("/api/inventory")
				.then()
				.log().all()
				.statusCode(200)
				.body(Matchers.equalTo("false")); // Assuming no stock available
	}
}
