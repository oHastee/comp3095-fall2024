package ca.gbc.productservice;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;

// Tells Spring Boot to look for a main configuration class (@SpringBootApplication)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	// This annotation is used in combination with TestContainers to automatically configure the connection to
	// The Test MongoDbContainer
	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

	@LocalServerPort
	private Integer port;

	//http://localhost:port/api/product
	@BeforeEach
	void setup(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mongoDBContainer.start();
	}

	@Test
	void createProductTest(){

		String requestBody = """
				{
				"name": "Samsung TV",
				"description": "Samsung TV - Model 2024",
				"price": 2000
				}
				""";

		// BDD -0 Behavioural Driven Development (Given, When , Then)
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/product")
				.then()
				.log().all()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("Samsung TV"))
				.body("description", Matchers.equalTo("Samsung TV - Model 2024"))
				.body("price", Matchers.equalTo(2000));

	}

	@Test
	void getAllProductTest(){

		String requestBody = """
				{
				"name": "Samsung TV",
				"description": "Samsung TV - Model 2024",
				"price": 2000
				}
				""";

		// BDD -0 Behavioural Driven Development (Given, When , Then)
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/product")
				.then()
				.log().all()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("Samsung TV"))
				.body("description", Matchers.equalTo("Samsung TV - Model 2024"))
				.body("price", Matchers.equalTo(2000));

		RestAssured.given()
				.contentType("application/json")
				.when()
				.get("/api/product")
				.then()
				.log().all()
				.statusCode(200)
				.body("size()", Matchers.greaterThan(0))
				.body("[0].name", Matchers.equalTo("Samsung TV"))
				.body("[0].description", Matchers.equalTo("Samsung TV - Model 2024"))
				.body("[0].price", Matchers.equalTo(2000));

	}

	void updateProductTest() {

		// Step 1: Create a new product
		String createRequestBody = """
            {
            "name": "Samsung TV",
            "description": "Samsung TV - Model 2024",
            "price": 2000
            }
            """;

		// Create the product and extract its ID
		String productId = RestAssured.given()
				.contentType("application/json")
				.body(createRequestBody)
				.when()
				.post("/api/product")
				.then()
				.statusCode(201)
				.extract()
				.path("id");

		// Step 2: Prepare updated product information
		String updateRequestBody = """
            {
            "name": "Samsung TV",
            "description": "Samsung TV - Model 2025",
            "price": 2100
            }
            """;

		// Send PUT request to update the product
		RestAssured.given()
				.contentType("application/json")
				.body(updateRequestBody)
				.when()
				.put("/api/product/" + productId)
				.then()
				.log().all()
				.statusCode(204); // Assuming the API returns 204 No Content on successful update

		// Step 3: Verify the product was updated
		RestAssured.given()
				.when()
				.get("/api/product/" + productId)
				.then()
				.statusCode(200)
				.body("id", Matchers.equalTo(productId))
				.body("name", Matchers.equalTo("Samsung TV"))
				.body("description", Matchers.equalTo("Samsung TV - Model 2025"))
				.body("price", Matchers.equalTo(2100));
	}

	void deleteProductTest() {

		// Step 1: Create a new product
		String createRequestBody = """
            {
            "name": "Samsung TV",
            "description": "Samsung TV - Model 2024",
            "price": 2000
            }
            """;

		// Create the product and extract its ID
		String productId = RestAssured.given()
				.contentType("application/json")
				.body(createRequestBody)
				.when()
				.post("/api/product")
				.then()
				.statusCode(201)
				.extract()
				.path("id");

		// Step 2: Delete the product
		RestAssured.given()
				.when()
				.delete("/api/product/" + productId)
				.then()
				.statusCode(204); // Assuming the API returns 204 No Content on successful deletion

		// Step 3: Verify the product has been deleted
		RestAssured.given()
				.when()
				.get("/api/product/" + productId)
				.then()
				.statusCode(404); // Assuming the API returns 404 Not Found when a product doesn't exist
	}



}
