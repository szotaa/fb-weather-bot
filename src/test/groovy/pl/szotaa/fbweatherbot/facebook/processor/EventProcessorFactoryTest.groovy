package pl.szotaa.fbweatherbot.facebook.processor

import pl.szotaa.fbweatherbot.facebook.communication.ResponseService
import pl.szotaa.fbweatherbot.facebook.processor.strategy.TextMessageEventProcessorStrategy
import spock.lang.Specification

class EventProcessorFactoryTest extends Specification {

    def responseService = Mock(ResponseService)
    def eventProcessorFactory = new EventProcessorFactory(responseService)

    def "TextMessageEventProcessorStrategy passed, should return correct EventProcessor"() {
        given:
            def strategy = new TextMessageEventProcessorStrategy()
        when:
            def eventProcessor = eventProcessorFactory.getEventProcessor(strategy)
        then:
            eventProcessor.processingStrategy instanceof TextMessageEventProcessorStrategy
            eventProcessor.responseService instanceof ResponseService
    }
}
