package com.example.eventdriven;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Turns on Spring's {@code @Async} support - and with it, asynchronous event listeners.
 *
 * <p><b>Spring application events are synchronous by default.</b> {@code publishEvent()} calls the
 * listeners one after another, on the caller's thread, and only returns when all of them are done.
 *
 * <p>Our listeners are annotated with {@code @Async}, but that annotation does nothing on its own.
 * Only when {@code @EnableAsync} is active does Spring wrap each listener in a proxy that hands the
 * call over to a background thread pool (Spring Boot's auto-configured {@code applicationTaskExecutor},
 * threads named {@code async-1}, {@code async-2}, ...). {@code publishEvent()} then returns at once.
 *
 * <p>This class is only loaded when {@code app.events.async=true} (the default, see
 * {@code application.properties}). Start the app with {@code --app.events.async=false} to see
 * Spring's default synchronous behaviour - no code changes needed.
 */
@Configuration
@EnableAsync
@ConditionalOnProperty(name = "app.events.async", havingValue = "true", matchIfMissing = true)
public class AsyncConfig {
}
