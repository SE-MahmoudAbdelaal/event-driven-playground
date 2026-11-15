package com.example.eventdriven.listener;

import com.example.eventdriven.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Sends the order confirmation e-mail.
 *
 * <p>It is independent of the other listeners: it neither knows nor waits for the inventory or the
 * payment. It reacts to the same event, in parallel with them (when async mode is on).
 */
@Component
public class NotificationListener {

    private static final Logger log = LoggerFactory.getLogger(NotificationListener.class);

    @Async // run on a background thread - only has an effect when @EnableAsync is active (see AsyncConfig)
    @EventListener
    public void onOrderCreated(OrderCreatedEvent event) throws InterruptedException {
        log.info("[orderId={}] Received OrderCreatedEvent -> sending confirmation email to {}",
                event.orderId(), event.customerEmail());

        Thread.sleep(500); // simulate talking to an e-mail server

        log.info("[orderId={}] Confirmation email sent to {}", event.orderId(), event.customerEmail());
    }
}
