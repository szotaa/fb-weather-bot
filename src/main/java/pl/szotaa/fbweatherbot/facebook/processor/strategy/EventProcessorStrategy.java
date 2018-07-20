package pl.szotaa.fbweatherbot.facebook.processor.strategy;

import com.github.messenger4j.webhook.event.BaseEvent;
import pl.szotaa.fbweatherbot.facebook.domain.Response;

/**
 * Strategy interface for processing events received from Facebook Messenger.
 *
 * @author szotaa
 */

public interface EventProcessorStrategy {
    Response processEvent(BaseEvent event);
}
