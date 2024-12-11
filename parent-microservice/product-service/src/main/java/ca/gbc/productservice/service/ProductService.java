package ca.gbc.productservice.service;

import ca.gbc.productservice.dto.ProductRequest; // DTO used for data transfer between the controller and service layers.
import ca.gbc.productservice.dto.ProductResponse; // DTO used for data transfer between the controller and service layers.

import java.util.List; // A Java collection used to handle a list of products.

public interface ProductService {
 // This declares an interface named ProductService.
 // An interface in Java is a contract that defines methods without implementing them,
 // meaning that any class that implements this interface must provide the logic
 // for these methods.
    ProductResponse createProduct(ProductRequest productRequest); // This method takes a ProductRequest object as a parameter and returns a ProductResponse. It's intended to handle the creation of a new product.
    List<ProductResponse> getAllProducts(); // This method returns a list of ProductResponse objects, representing all products available. It allows retrieval of all products.
    String updateProduct(String id, ProductRequest productRequest); // This method takes a product ID (String id) and a ProductRequest object to update an existing product. It returns a String, likely the ID of the updated product.
    void deleteProduct(String id); // This method takes a product ID (String id) to delete a product. It returns no value (void) since the action is a deletion.
}

// Purpose of ProductService.java:
// The ProductService interface defines the contract for the product-related business logic
// in your application. It specifies the methods that any class implementing this interface
// must have, ensuring a consistent way to handle product operations like creating,
// retrieving, updating, and deleting products.