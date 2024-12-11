package ca.gbc.productservice;

import org.springframework.boot.SpringApplication; //  A class that provides a convenient way to bootstrap a Spring application from a Java main method.
import org.springframework.boot.autoconfigure.SpringBootApplication; // A convenience annotation that adds several important annotations:
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication // This annotation marks the class as a Spring Boot application.
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}
   // SpringApplication.run(): This method starts the Spring application. It creates the Spring application context,
	// which is responsible for managing the lifecycle of the application. The method takes two parameters:
	//ProductServiceApplication.class: This indicates the primary Spring component (the application itself) that Spring should use.
	//args: This allows command-line arguments to be passed to the application.
}


// The ProductServiceApplication class is the entry point of your Spring Boot application.
// It contains the main method that launches the application. This file is crucial for starting up
// your application and initializing the Spring context, which in turn wires together all the components
// you've defined in your application (like controllers, services, repositories, etc.).