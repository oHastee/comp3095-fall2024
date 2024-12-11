package ca.gbc.productservice.dto;

import java.math.BigDecimal;

public record ProductResponse(

        String id,
        String name,
        String description,
        BigDecimal price
) { }

// Purpose of ProductResponse.java
//Use Case: This class is used to encapsulate the data that will be sent back to
// the client when a product is retrieved or when a response is needed after a product
// is created or updated. It ensures that the client receives all relevant information
// about the product in a structured format.