package ca.gbc.productservice.controller;

import ca.gbc.productservice.dto.ProductRequest;
import ca.gbc.productservice.dto.ProductResponse;
import ca.gbc.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


// The ProductController class handles HTTP requests related to products
// in your application. It provides endpoints for creating, retrieving, updating,
// and deleting products, effectively serving as an interface between
// the client and the backend logic managed by the ProductService.


@RestController //@RestController: Indicates that this class serves as a REST controller, meaning it handles HTTP requests and responses.

@RequestMapping("/api/product") // Specifies that all routes in this controller will start with /api/product. This is a way to group related endpoints.
@RequiredArgsConstructor // A Lombok annotation that generates a constructor with required arguments (final fields).
// Here, it automatically creates a constructor for ProductService.
public class ProductController {

    private final ProductService productService; // allowing this controller to use the service's methods to perform business logic.




    @PostMapping // Maps HTTP POST requests to the createProduct method. This is used to create a new product.
    @ResponseStatus(HttpStatus.CREATED) // Sets the response status to 201 Created when a product is successfully created.
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){ //This method accepts a ProductRequest object in the request body,
        // which contains the data needed to create a new product.
        // It returns a ResponseEntity containing a ProductResponse.

        ProductResponse createdProduct = productService.createProduct(productRequest); // Inside the method, it calls productService.createProduct(productRequest) to create the product.

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/product" + createdProduct.id()); // Sets the Location header to the URL of the newly created product and returns it.

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body(createdProduct);


    }




    @GetMapping // Maps HTTP GET requests to the getAllProducts method, used to retrieve all products.
    @ResponseStatus(HttpStatus.OK) // sets the http status to 200 which means the process of retrieving the data was successful
    public List<ProductResponse> getAllProducts() {
        // Returns a list of all products as ProductResponse objects
        // by calling the corresponding method in the ProductService.

        //try {
        //    Thread.sleep(5000);
        //}catch(InterruptedException e){
        //    throw new RuntimeException(e);
       // }
        return productService.getAllProducts();
    }






    //http://localhost:8080/api/product/jlgjfhkjghfkjhgdfkj
    @PutMapping("/{productID}") // Maps HTTP PUT requests to the updateProduct method, allowing updates to an existing product identified by productID.
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> updateProduct(@PathVariable("productID") String productID,
                                           @RequestBody ProductRequest productRequest){
        // Accepts the product ID in the URL and the updated data in the request body.
        // It calls productService.updateProduct(productID, productRequest)
        // to perform the update.
        String updatedProductID = productService.updateProduct(productID, productRequest);

        //set the location header attribute
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/product/" + updatedProductID); // Sets the Location header for the updated product and returns 204 No Content as a response.
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT); //returns 204 No Content as a response.
    }






    @DeleteMapping("/{productID}") // Maps HTTP DELETE requests to the deleteProduct method, allowing the deletion of a product identified by productID.
    public ResponseEntity<?> deleteProduct(@PathVariable("productID")String productID){
        // Accepts the product ID in the URL,
        // calls productService.deleteProduct(productID) to perform the deletion,
        // and returns 204 No Content to indicate success.
        productService.deleteProduct(productID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // returns 204 No Content as a response.
    }

}