package com.example.eventdriven.dto;

/**
 * Request body of {@code POST /api/orders}:
 *
 * <pre>
 * { "productId": 101, "quantity": 2, "customerEmail": "customer@example.com" }
 * </pre>
 *
 * <p>DTOs (Data Transfer Objects) describe the shape of the API. Keeping them separate from the
 * domain model ({@code Order}) lets the public API and the internal model change independently.
 */
public record CreateOrderRequest(Long productId, int quantity, String customerEmail) {
}
