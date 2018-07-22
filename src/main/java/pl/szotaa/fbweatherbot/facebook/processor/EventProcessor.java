package pl.szotaa.fbweatherbot.facebook.processor;

import com.github.messenger4j.webhook.event.BaseEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import pl.szotaa.fbweatherbot.facebook.communication.ResponseService;
import pl.szotaa.fbweatherbot.facebook.domain.Request;
import pl.szotaa.fbweatherbot.facebook.processor.strategy.EventProcessorStrategy;

/**
 * Processes different kinds of events received from Facebook Messenger and acts according to passed strategy.
 * @see EventProcessorStrategy
 * @see EventProcessorFactory
 *
 * @author szotaa
 */

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class EventProcessor {

    private final ResponseService responseService;
    private final EventProcessorStrategy processingStrategy;

    public void process(BaseEvent event){
        Request result = this.processingStrategy.processEvent(event);
        responseService.respond(result, event.senderId());
    }
}
