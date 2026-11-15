package com.example.eventdriven.service;

import com.example.eventdriven.event.OrderCreatedEvent;
import com.example.eventdriven.model.Order;
import com.example.eventdriven.model.OrderStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A plain unit test: no Spring context and no listeners.
 *
 * <p>OrderService only depends on {@code ApplicationEventPublisher}, so we can hand it a fake publisher
 * that simply records the events. This is one benefit of event-based communication: the publisher
 * can be tested without any of the components that react to its events.
 */
class OrderServiceTest {

    private final List<Object> publishedEvents = new ArrayList<>();
    private final OrderService orderService = new OrderService(event -> publishedEvents.add(event));

    @Test
    void createOrderPublishesOrderCreatedEvent() {
        Order order = orderService.createOrder(101L, 2, "customer@example.com");

        assertThat(order.id()).isEqualTo(1L);
        assertThat(order.status()).isEqualTo(OrderStatus.CREATED);

        assertThat(publishedEvents).singleElement()
                .isInstanceOfSatisfying(OrderCreatedEvent.class, event -> {
                    assertThat(event.orderId()).isEqualTo(order.id());
                    assertThat(event.productId()).isEqualTo(101L);
                    assertThat(event.quantity()).isEqualTo(2);
                    assertThat(event.customerEmail()).isEqualTo("customer@example.com");
                    assertThat(event.occurredAt()).isNotNull();
                });
    }
}
