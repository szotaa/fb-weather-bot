package pl.szotaa.fbweatherbot.facebook.processor.strategy;

import com.github.messenger4j.webhook.event.BaseEvent;
import pl.szotaa.fbweatherbot.facebook.domain.Request;

/**
 * Strategy interface for processing events received from Facebook Messenger.
 *
 * @author szotaa
 */

public interface EventProcessorStrategy {
    Request processEvent(BaseEvent event);
}
