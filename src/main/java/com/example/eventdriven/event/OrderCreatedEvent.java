package com.example.eventdriven.event;

import java.time.Instant;

/**
 * An event is a message that says "something has happened".
 *
 * <ul>
 *   <li>Its name is in the <b>past tense</b>: the order <i>was</i> created. It is a fact, not a
 *       command such as "ReserveInventory" - the publisher doesn't tell anyone what to do.</li>
 *   <li>It is <b>immutable</b> (a record), so it can safely be handed to many listeners,
 *       even on different threads.</li>
 *   <li>It carries the <b>data the listeners need</b>, so they never have to call back into
 *       {@code OrderService}.</li>
 *   <li>It records <b>when</b> it happened ({@code occurredAt}, in UTC).</li>
 * </ul>
 *
 * <p>Since Spring 4.2 an event can be any object - it doesn't have to extend {@code ApplicationEvent}.
 * Spring wraps it in a {@code PayloadApplicationEvent} internally.
 */
public record OrderCreatedEvent(
        Long orderId,
        Long productId,
        int quantity,
        String customerEmail,
        Instant occurredAt) {
}
