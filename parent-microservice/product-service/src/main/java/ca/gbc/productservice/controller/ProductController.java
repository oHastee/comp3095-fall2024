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

import java.util.List;

//Useful for working with HTML payload, but we are using JSON payload
// @Controller
@RestController //derivative controller for JSON
@RequestMapping("/api/product")
@RequiredArgsConstructor

public class ProductController {

    private final ProductService productService;

    @PostMapping // Maps HTTP POST requests to this method. Typically used for creating new resources.
    @ResponseStatus(HttpStatus.CREATED) // Sets the response status to 201 Creted when a product is succesfully created.
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){

        //The @RequestBody annotation indicates that the request body contains a ProductRequest object.
        ProductResponse createdProduct = productService.createProduct(productRequest);

        //Set the headers (e.g., Location header if you want to indicate the URL of the created resource)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/product" + createdProduct.id());

        // Return the ResponseEntity with the 201 Created status, response body, and headers.
        return ResponseEntity
                .status(HttpStatus.CREATED) // Set status to 201
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON) // Set content type to JSON
                .body(createdProduct); // Return the created product in the response body
    }

    @GetMapping //Maps HTTP GET requests to this method. Typically used to retrieve resources.
    @ResponseStatus(HttpStatus.OK) // Sets the response status to 200 OK when the products are succesfully retrieved.
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    //put = updating complete option, patch = updating fraction
    //http://localhost:8080/api/product/jklghjkl
    @PutMapping("/{productId}") // Maps HTTP PUT requests to this method. The {productId} is a path variable used to identity which
    // ResponseEntity<?> is used when you want to return an HTTP response without specifying the type of the response body, meaning
    public ResponseEntity<?> updateProduct(@PathVariable("productId") String productId,
                                           @RequestBody ProductRequest productRequest){

        String updatedProductId = productService.updateProduct(productId, productRequest);

        //set the location header attribute
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","api/product" + updatedProductId);

        //alternative to @ResponseStatus(HttpStatus.NO_CONTENT):
        return new ResponseEntity<>(headers,HttpStatus.NO_CONTENT);
    }

    //http://localhost:8080/api/product/jklghjkl
    @DeleteMapping("/{productId}") // Maps HTTP DELETE requests to this method to delete a product identified by its ID.
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") String productId){
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}