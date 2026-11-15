package com.example.eventdriven.service;

import com.example.eventdriven.event.OrderCreatedEvent;
import com.example.eventdriven.model.Order;
import com.example.eventdriven.model.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Creates orders and announces them by publishing an {@link OrderCreatedEvent}.
 *
 * <p>Look at the imports: there is no InventoryListener, NotificationListener or PaymentListener.
 * {@code OrderService} only knows the event and the {@link ApplicationEventPublisher}.
 *
 * <p>For comparison, with <b>direct method invocation</b> {@code createOrder} would end like this:
 * <pre>
 *     inventoryService.reserve(order);       // OrderService must know every collaborator,
 *     notificationService.sendEmail(order);  // waits until each one has finished,
 *     paymentService.charge(order);          // and must be edited for every new requirement.
 * </pre>
 * With events it simply announces "an order was created" and Spring delivers that fact to whoever
 * is interested - three listeners today, maybe five tomorrow, without touching this class.
 */
@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final ApplicationEventPublisher eventPublisher;

    // No database in this example: orders are simply kept in memory.
    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong nextOrderId = new AtomicLong();

    public OrderService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public Order createOrder(Long productId, int quantity, String customerEmail) {
        // 1. Do our own job: create and store the order.
        Order order = new Order(
                nextOrderId.incrementAndGet(), productId, quantity, customerEmail, OrderStatus.CREATED);
        orders.put(order.id(), order);
        log.info("[orderId={}] Order created and stored with status {}", order.id(), order.status());

        // 2. Tell the rest of the application what happened - without knowing who is listening.
        OrderCreatedEvent event = new OrderCreatedEvent(
                order.id(), order.productId(), order.quantity(), order.customerEmail(), Instant.now());

        log.info("[orderId={}] Publishing {}", order.id(), event);
        long start = System.currentTimeMillis();

        eventPublisher.publishEvent(event);

        // Synchronous mode:  we only get here after ALL listeners have finished.
        // Asynchronous mode: we get here immediately - the listeners are still running.
        log.info("[orderId={}] publishEvent() returned after {} ms", order.id(), System.currentTimeMillis() - start);

        return order;
    }
}
