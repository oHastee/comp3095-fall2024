package ca.gbc.orderservice.service;

import ca.gbc.orderservice.dto.OrderRequest;
import ca.gbc.orderservice.dto.OrderResponse;
import ca.gbc.orderservice.model.Order;
import ca.gbc.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest) {

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .price(orderRequest.price())
                .skuCode(orderRequest.skuCode())
                .quantity(orderRequest.quantity())
                .build();

        // Save the order to the database
        Order savedOrder = orderRepository.save(order);
        // Create and return an OrderResponse from the saved Order
        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getOrderNumber(),
                savedOrder.getSkuCode(),
                savedOrder.getPrice(),
                savedOrder.getQuantity()
        );
    }
}
