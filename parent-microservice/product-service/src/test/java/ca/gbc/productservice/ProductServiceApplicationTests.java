package ca.gbc.productservice;

// This file contains unit tests for the ProductServiceApplication. It uses Spring Boot's testing framework
// along with RestAssured for testing RESTful services. The tests verify the behavior
// of the product service API endpoints.



import io.restassured.RestAssured; //  Used for making HTTP requests and validating responses.
import org.hamcrest.Matchers; // Provides matchers for validating response properties.

// JUnit annotations for setup and defining test methods.
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest; // Used to load the application context for integration testing.
import org.springframework.boot.test.web.server.LocalServerPort; //  Used to get the random port assigned to the embedded server.
import org.springframework.boot.testcontainers.service.connection.ServiceConnection; // Used with TestContainers to manage service connections.
import org.testcontainers.containers.MongoDBContainer; //  A TestContainer class for managing MongoDB instances.


// Spring Boot Test Configuration: The class is annotated with @SpringBootTest, indicating that it is
// a Spring Boot test and it should start the application context.
// The webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT setting tells Spring to start the server
// on a random available port for the tests.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	// This line initializes a static MongoDB container instance using the latest version of MongoDB.
	// The @ServiceConnection annotation automatically manages the connection to this container during the tests.
	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

	// The @LocalServerPort annotation injects the random port assigned to the embedded server into the port variable,
	// allowing the tests to know which port to use for API calls.
	@LocalServerPort
	private Integer port;

	//http://localhost:port/api/product
	// The @BeforeEach annotation indicates that this method will run before each test.
	// It configures the base URI and port for RestAssured, ensuring that all API requests use the correct URL.
	@BeforeEach
	void setup(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		// This block starts the MongoDB container before any tests run.
		// The container will run in the background and can be used for testing the application
		// against a real MongoDB instance.
		mongoDBContainer.start();
	}

	@Test // Test Annotation: Marks the method as a test to be run by JUnit.
	void createProductTest(){

		// Request Body: Defines a JSON string representing a product to be created.
		String requestBody = """
				{
				"name" : "Samsung TV",
				"description" : "Samsung TV - Model 2024",
				"price" : 2000
				}
				""";

		// BDD -0 Behavioural Driven Development (Given, When , Then)
		// Request and Response Validation: This block makes a POST request to the /api/product endpoint
		// with the defined request body. It checks that:
		//The response has a status code of 201 (created).
		//The response body contains the correct fields and values (e.g., id, name, description, and price).
		RestAssured.given() // This method begins a new request. It prepares the request builder and allows you to specify details about the request you want to make, such as headers, body, and parameters.
				.contentType("application/json") // This specifies that the content type of the request body is JSON.
				.body(requestBody) // This method sets the body of the request. The requestBody variable contains the JSON representation of the product to be created. In this context, it includes the product's name, description, and price.
				.when() // This method says that you're ready to define what type of HTTP request you want to make.
				.post("/api/product") // This line indicates that you are making a POST request to the specified URL endpoint (/api/product).
				.then() // After the request has been executed, this method allows you to define expectations for the response.
				.log().all() // This method logs the details of the request and response to the console. It provides visibility into what was sent and what was received
				.statusCode(201) // This line checks that the response status code is 201, which indicates that a resource has been successfully created
				.body("id", Matchers.notNullValue()) // This checks that the response body contains an id field and that it is not null.
				.body("name", Matchers.equalTo("Samsung TV")) // This checks that the name field in the response body matches the expected value of "Samsung TV"
				.body("description", Matchers.equalTo("Samsung TV - Model 2024")) //  this checks that the description field in the response matches the expected value.
				.body("price", Matchers.equalTo(2000)); // This line checks that the price field in the response body matches the expected value of 2000.

	}

	@Test // Marks this method as a separate test for getting all products.
	void getAllProductTest(){

		String requestBody = """
				{
				"name": "Samsung TV",
				"description": "Samsung TV - Model 2024",
				"price": 2000
				}
				""";

		// BDD -0 Behavioural Driven Development (Given, When , Then)
		// This block sends a GET request to the /api/product endpoint, validating that:
		// The response has a status code of 200 (OK).
		//The response contains at least one product.
		//The first product in the response has the expected attributes.
		RestAssured.given() // This method begins a new request. It prepares the request builder and allows you to specify details about the request you want to make, such as headers, body, and parameters.
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

}