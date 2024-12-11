package ca.gbc.productservice.repository;

import ca.gbc.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository; // An interface provided by Spring Data MongoDB,
// which offers basic CRUD operations.

public interface ProductRepository extends MongoRepository<Product, String> {
    // This declares an interface named ProductRepository. By extending MongoRepository<Product, String>,
    // this interface inherits methods for CRUD operations without needing to implement them.

    // MongoRepository<Product, String> indicates that the repository will handle Product entities,
    // where String is the type of the ID of the product.
}


// Purpose of ProductRepository.java
//The ProductRepository interface is responsible for data access operations related to the Product model.
// By extending MongoRepository, it provides methods such as:
//save(): To save a product.
//findById(): To find a product by its ID.
//findAll(): To retrieve all products.
//delete(): To delete a product.

// This means that you do not have to write boilerplate code for these common operations,
// as Spring Data will generate the necessary implementation at runtime.

// Key Terms Explained:
//Repository: A design pattern that mediates between the domain and data mapping layers,
// providing an abstraction for data access. It typically defines methods for accessing and manipulating data.

//MongoRepository: A Spring Data interface that provides CRUD functionality for MongoDB databases.
// It simplifies the interaction with the database, allowing you to perform operations without writing specific queries.