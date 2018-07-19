package pl.szotaa.fbweatherbot.facebook.controller

import com.github.messenger4j.Messenger
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.szotaa.fbweatherbot.facebook.service.MessengerService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class MessengerControllerTest extends Specification {

    def messengerService = Mock(MessengerService)
    def messengerController = new MessengerController(messengerService)
    def mockMvc = MockMvcBuilders.standaloneSetup(messengerController).build()

    def 'Verifying web hook with correct parameters should return the same string as passed challenge string'() {
        given:
            def mode = "subscribe"
            def token = "token"
            def challenge = "challenge"

        when:
            def response = mockMvc.perform(get("/")
                                .param(Messenger.MODE_REQUEST_PARAM_NAME, mode)
                                .param(Messenger.VERIFY_TOKEN_REQUEST_PARAM_NAME, token)
                                .param(Messenger.CHALLENGE_REQUEST_PARAM_NAME, challenge))

        then:
            1 * messengerService.verifyWebHook(!null as String, !null as String)

            response
                .andExpect(status().isOk())
                .andExpect(content().string(challenge))
    }
}
