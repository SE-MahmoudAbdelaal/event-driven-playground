package com.example.eventdriven.dto;

import com.example.eventdriven.model.OrderStatus;

/**
 * Response body of {@code POST /api/orders}:
 *
 * <pre>
 * { "orderId": 1, "status": "CREATED" }
 * </pre>
 */
public record CreateOrderResponse(Long orderId, OrderStatus status) {
}
