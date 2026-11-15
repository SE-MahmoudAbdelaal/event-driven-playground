package com.example.eventdriven.model;

/**
 * The domain object: an order placed by a customer.
 *
 * <p>It is a Java record, so it is immutable - simple to reason about and safe to share between threads.
 */
public record Order(
        Long id,
        Long productId,
        int quantity,
        String customerEmail,
        OrderStatus status) {
}
