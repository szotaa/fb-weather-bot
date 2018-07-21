package pl.szotaa.fbweatherbot.facebook.verification

import com.github.messenger4j.Messenger
import com.github.messenger4j.exception.MessengerVerificationException
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.anyString
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class WebHookVerificationServiceTest extends Specification {

    def messenger = mock(Messenger.class)
    def webHookVerificationService = new WebHookVerificationService(messenger)

    def "Verifying web hook with incorrect parameters should raise exception"() {
        given:
            def mode = "mode"
            def token = "token"
        when:
            when(messenger.verifyWebhook(anyString(), anyString())).thenThrow(MessengerVerificationException.class)
            webHookVerificationService.verifyWebHook(mode, token)
        then:
            thrown(MessengerVerificationException)
    }
}
