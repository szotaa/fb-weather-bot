package pl.szotaa.fbweatherbot.facebook.processor.strategy;

import com.github.messenger4j.webhook.event.BaseEvent;
import com.github.messenger4j.webhook.event.TextMessageEvent;
import pl.szotaa.fbweatherbot.facebook.domain.GetWeatherSkipSearchRequest;
import pl.szotaa.fbweatherbot.facebook.domain.Request;
import pl.szotaa.fbweatherbot.facebook.domain.UnknownRequest;

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
    public Request processEvent(BaseEvent event) {
        TextMessageEvent messageEvent = (TextMessageEvent) event;
        String text = messageEvent.text().toLowerCase();
        if(text.startsWith("weather ")) {
            return new GetWeatherSkipSearchRequest(text.substring(7, text.length()));
        } else {
            return new UnknownRequest(text);
        }
    }
}
