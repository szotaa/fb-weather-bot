package pl.szotaa.fbweatherbot.facebook.service

import com.github.messenger4j.Messenger
import spock.lang.Ignore
import spock.lang.Specification

class MessengerServiceTest extends Specification {

    Messenger messenger = GroovyMock()
    MessengerService messengerService = new MessengerService(messenger)

    @Ignore//TODO: fix not working mock
    def "Correct values passed for web hook verification, method executes without exception"() {
        given:
            def mode = "subscribe"
            def verifyToken = "verifyToken"

        when:
            messengerService.verifyWebHook(mode, verifyToken)

        then:
           1 * messenger.verifyWebhook(_, _)
    }
}
