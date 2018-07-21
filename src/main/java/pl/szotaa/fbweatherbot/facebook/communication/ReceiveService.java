package pl.szotaa.fbweatherbot.facebook.communication;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerVerificationException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.szotaa.fbweatherbot.facebook.processor.EventProcessor;
import pl.szotaa.fbweatherbot.facebook.processor.EventProcessorFactory;
import pl.szotaa.fbweatherbot.facebook.processor.strategy.TextMessageEventProcessorStrategy;

/**
 * Discovers type of received payload and and acts accordingly.
 *
 * @author szotaa
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiveService {

    private final Messenger messenger;
    private final EventProcessorFactory eventProcessorFactory;

    public void handleReceivedPayload(String payload, String signature) throws MessengerVerificationException {
        Optional<String> signatureOptional = Optional.ofNullable(signature);
        this.messenger.onReceiveEvents(payload, signatureOptional, event -> {
            if(event.isTextMessageEvent()){
                EventProcessor eventProcessor = eventProcessorFactory.getEventProcessor(new TextMessageEventProcessorStrategy());
                eventProcessor.process(event.asTextMessageEvent());
            }
            else {
                throw new UnsupportedOperationException("Received payload not supported.");
            }
        });
    }
}
