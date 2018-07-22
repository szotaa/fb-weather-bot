package pl.szotaa.fbweatherbot.facebook.processor.strategy;

import com.github.messenger4j.webhook.event.BaseEvent;
import com.github.messenger4j.webhook.event.TextMessageEvent;
import java.util.Arrays;
import java.util.List;
import pl.szotaa.fbweatherbot.facebook.domain.GetWeatherSkipSearchRequest;
import pl.szotaa.fbweatherbot.facebook.domain.Request;

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
        List<String> words = Arrays.asList(messageEvent.text().split("\\s+"));
        if(words.contains("weather") || words.size() == 2 || words.indexOf("weather") == 0) {
            return new GetWeatherSkipSearchRequest(words.get(1));
        } else {
            throw new RuntimeException("message not readable");
        }
    }
}
