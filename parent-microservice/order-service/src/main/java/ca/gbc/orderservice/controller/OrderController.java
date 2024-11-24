package ca.gbc.orderservice.controller;

import ca.gbc.orderservice.dto.OrderRequest;
import ca.gbc.orderservice.dto.OrderResponse;
import ca.gbc.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor

public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, Object>> placeOrder(@RequestBody OrderRequest orderRequest) {

        // Place the order and get the created order details
        OrderResponse createdOrder = orderService.placeOrder(orderRequest);

        // Set the headers (e.g., Location header)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/order/" + createdOrder.id());

        // Prepare the response body with a success message and the created order
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Order placed successfully");
        responseBody.put("order", createdOrder);

        // Return the response with status, headers, and body
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseBody);
    }
}
