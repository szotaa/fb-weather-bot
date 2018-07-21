package pl.szotaa.fbweatherbot.facebook.communication

import com.github.messenger4j.Messenger
import pl.szotaa.fbweatherbot.facebook.processor.EventProcessorFactory
import spock.lang.Specification

import java.util.function.Consumer

import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.anyString
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.times
import static org.mockito.Mockito.verify

class ReceiveServiceTest extends Specification {

    def messenger = mock(Messenger)
    def eventProcessorFactory = Mock(EventProcessorFactory)
    def messengerService = new ReceiveService(messenger, eventProcessorFactory)

    def "Messenger called with proper arguments when received payload is handled"() {
        given:
            def payload = "payload"
            def signature = "appSecret"

        when:
            messengerService.handleReceivedPayload(payload, signature)

        then:
            verify(messenger, times(1)).onReceiveEvents(anyString(), any(Optional.class), any(Consumer.class))
    }
}
