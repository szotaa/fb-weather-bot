package pl.szotaa.fbweatherbot.facebook.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.szotaa.fbweatherbot.facebook.communication.ResponseService;
import pl.szotaa.fbweatherbot.facebook.processor.strategy.EventProcessorStrategy;

/**
 * Instantiates EventProcessors with given {@link EventProcessorStrategy} and always the same
 * reference to {@link ResponseService}.
 *
 * @see EventProcessor
 * @see EventProcessorStrategy
 * @see ResponseService
 *
 * @author szotaa
 */

@Component
@RequiredArgsConstructor
public class EventProcessorFactory {

    private final ResponseService responseService;

    public EventProcessor getProcessingContext(EventProcessorStrategy strategy){
        return new EventProcessor(responseService, strategy);
    }
}
