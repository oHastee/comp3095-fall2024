package ca.gbc.productservice.dto;

import java.math.BigDecimal;


//This line defines a Java record called ProductRequest.
// Records are a special type of class in Java designed to hold immutable data in a concise way.
// They automatically provide methods like toString(), equals(),
// and hashCode() based on their fields.
public record ProductRequest(

        String id,
        String name,
        String description,
        BigDecimal price
) { }

// Purpose of ProductRequest.java:

//Use Case: This class is typically used to encapsulate the data needed
// to create or update a product. It's designed to be passed
// from the client to the server when a new product is being created or
// an existing product is being updated. This way,
// you can send all relevant information in a single object.