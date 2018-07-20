package pl.szotaa.fbweatherbot.facebook.communication

import com.github.messenger4j.Messenger
import spock.lang.Ignore
import spock.lang.Specification

class ReceiveServiceTest extends Specification {

    Messenger messenger = GroovyMock()
    ReceiveService messengerService = new ReceiveService(messenger)

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
