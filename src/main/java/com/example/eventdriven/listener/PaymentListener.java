package com.example.eventdriven.listener;

import com.example.eventdriven.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Charges the customer for every new order.
 *
 * <p>It is independent of the other listeners: it only needs the data inside the event.
 */
@Component
public class PaymentListener {

    private static final Logger log = LoggerFactory.getLogger(PaymentListener.class);

    @Async // run on a background thread - only has an effect when @EnableAsync is active (see AsyncConfig)
    @EventListener
    public void onOrderCreated(OrderCreatedEvent event) throws InterruptedException {
        log.info("[orderId={}] Received OrderCreatedEvent -> processing payment for {} x product {}",
                event.orderId(), event.quantity(), event.productId());

        Thread.sleep(1500); // simulate a call to an external payment provider

        log.info("[orderId={}] Payment processed", event.orderId());
    }
}
