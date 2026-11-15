package com.example.eventdriven.controller;

import com.example.eventdriven.dto.CreateOrderRequest;
import com.example.eventdriven.dto.CreateOrderResponse;
import com.example.eventdriven.model.Order;
import com.example.eventdriven.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The HTTP entry point. It translates JSON (DTOs) to a service call and back - nothing else.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest request) {
        log.info("==> POST /api/orders {}", request);

        Order order = orderService.createOrder(request.productId(), request.quantity(), request.customerEmail());

        CreateOrderResponse response = new CreateOrderResponse(order.id(), order.status());
        log.info("[orderId={}] <== 201 Created {}", order.id(), response);
        return response;
    }
}
