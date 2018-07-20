package pl.szotaa.fbweatherbot.facebook.processor.strategy;

import com.github.messenger4j.webhook.event.BaseEvent;
import pl.szotaa.fbweatherbot.facebook.domain.TextWeatherResponse;
import pl.szotaa.fbweatherbot.facebook.domain.Response;

/**
 * Implementation of {@link EventProcessorStrategy} which handles received text messages.
 *
 * @see pl.szotaa.fbweatherbot.facebook.processor.EventProcessor
 * @see EventProcessorStrategy
 *
 * @author szotaa
 */

public final class TextMessageEventProcessorStrategy implements EventProcessorStrategy {

    @Override
    public Response processEvent(BaseEvent event) {
        //TODO: implement
        return new TextWeatherResponse();
    }
}
