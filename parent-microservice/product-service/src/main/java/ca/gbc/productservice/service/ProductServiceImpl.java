package ca.gbc.productservice.service;

import ca.gbc.productservice.dto.ProductRequest;
import ca.gbc.productservice.dto.ProductResponse;
import ca.gbc.productservice.model.Product;
import ca.gbc.productservice.repository.ProductRepository; // for data access operations.
import lombok.RequiredArgsConstructor; // A Lombok annotation that generates a constructor with required arguments (final fields).
import lombok.extern.slf4j.Slf4j; // for logging.

// MongoTemplate, Criteria, and Query for MongoDB operations.
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.stereotype.Service; // Marks this class as a Spring service component, making it eligible for component scanning and dependency injection.

import java.util.List; // List for handling collections of products.

@Service // Marks this class as a Spring service component, making it eligible for component scanning and dependency injection.
@Slf4j // Provides a logger instance (log) to log messages.
@RequiredArgsConstructor // // A Lombok annotation that generates a constructor with required arguments (final fields).
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository; // This is a reference to the ProductRepository, allowing access to database operations.
    private final MongoTemplate mongoTemplate; // This is used for more advanced MongoDB operations beyond basic CRUD.

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
// Takes a ProductRequest object that contains details about the product to be created.

        log.debug("Creating a new product {}", productRequest.name()); // Logs a debug message with the product's name.

        Product product = Product.builder() // Creates a Product object using a builder pattern, mapping properties from ProductRequest.
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();

        // Persist a product
        productRepository.save(product); // Saves the product using productRepository.
        log.info("Product {} is saved", product.getId()); // Logs an info message after saving.

        return new ProductResponse(product.getId(), product.getName(),
                product.getDescription(), product.getPrice()); // Returns a ProductResponse with the saved product's details.

    }

    @Override
    public List<ProductResponse> getAllProducts() {
// A list of ProductResponse objects.

        log.debug("Returning a list of products"); // Logs a debug message indicating the retrieval of products.
        List<Product> products = productRepository.findAll(); // Uses productRepository to fetch all products and maps them to ProductResponse using a stream.

        //return products.stream().map(product -> mapToProductResponse(product)).toList();
        return products.stream().map(this::mapToProductResponse).toList();

    }

    private ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(),
                product.getDescription(), product.getPrice());
    }

    @Override
    public String updateProduct(String id, ProductRequest productRequest) {
//  Takes the product ID and the new details from ProductRequest.

        log.debug("Updating a product with id {}", id); // Logs a debug message with the product ID.

        Query query = new Query(); // Creates a Query to find the product in the database.
        query.addCriteria(Criteria.where("id").is(id)); //  means that the query is looking for a product document where the id field matches the given id.
        Product product = mongoTemplate.findOne(query, Product.class); // The findOne() method of the MongoTemplate class
        // is used to query the database and retrieve a single document that matches the specified criteria.
        //If multiple documents match the query, it returns the first one it finds. If no documents match,
        // it returns null.
        if (product != null){ // If found, updates the product's details and saves it back to the database.
            product.setName(productRequest.name());
            product.setDescription(productRequest.description());
            product.setPrice(productRequest.price());
            return productRepository.save(product).getId();
        } else {
                log.warn("No product found for ID: {}", id);

        }

        return id; // Returns the updated product's ID.

    }

    @Override
    public void deleteProduct(String id) { // Takes the product ID to delete.

        log.debug("Deleting a product with id {}", id); // Logs a debug message with the product ID.

        productRepository.deleteById(id); // Uses the repository to delete the product from the database.

    }
}


// Difference between this class and product controller:
//ProductController: Manages HTTP requests and responses, serving as the entry point for API interactions.
//ProductServiceImpl: Contains business logic and interacts with the repository layer to manage product data.