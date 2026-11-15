package com.example.eventdriven.listener;

import com.example.eventdriven.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Reserves stock for every new order.
 *
 * <p>Like the other listeners it knows nothing about {@code OrderService} or the other listeners -
 * it only knows the event. Spring finds the method at startup because of {@code @EventListener};
 * the <b>parameter type</b> ({@link OrderCreatedEvent}) decides which events it receives.
 */
@Component
public class InventoryListener {

    private static final Logger log = LoggerFactory.getLogger(InventoryListener.class);

    /** Every product pretends to have 10 units in stock. Order more to see what happens when a listener fails. */
    private static final int AVAILABLE_STOCK = 10;

    @Async // run on a background thread - only has an effect when @EnableAsync is active (see AsyncConfig)
    @EventListener
    public void onOrderCreated(OrderCreatedEvent event) throws InterruptedException {
        log.info("[orderId={}] Received OrderCreatedEvent -> reserving {} unit(s) of product {}",
                event.orderId(), event.quantity(), event.productId());

        if (event.quantity() > AVAILABLE_STOCK) {
            throw new IllegalStateException("[orderId=" + event.orderId() + "] Not enough stock for product "
                    + event.productId() + ": requested " + event.quantity() + ", available " + AVAILABLE_STOCK);
        }

        Thread.sleep(1000); // simulate slow work, e.g. a call to the warehouse system

        log.info("[orderId={}] Inventory reserved", event.orderId());
    }
}
