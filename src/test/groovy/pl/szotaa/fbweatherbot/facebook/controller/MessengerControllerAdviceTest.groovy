package pl.szotaa.fbweatherbot.facebook.controller

import com.github.messenger4j.Messenger
import com.github.messenger4j.exception.MessengerVerificationException
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.szotaa.fbweatherbot.facebook.service.MessengerService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class MessengerControllerAdviceTest extends Specification {

    def messengerService = Mock(MessengerService)
    def messengerController = new MessengerController(messengerService)
    def messengerControllerAdvice = new MessengerControllerAdvice()
    def mockMvc = MockMvcBuilders
            .standaloneSetup(messengerController)
            .setControllerAdvice(messengerControllerAdvice)
            .build()

    def 'Verifying web hook with incorrect parameters should return 400 bad request response status.'(){
        given:
            def mode = "subscribe"
            def token = "token"
            def challenge = "challenge"

        when:
            messengerService.verifyWebHook(_  as String, _ as String) >> {throw new MessengerVerificationException("")}
            def response = mockMvc.perform(get("/")
                    .param(Messenger.MODE_REQUEST_PARAM_NAME, mode)
                    .param(Messenger.VERIFY_TOKEN_REQUEST_PARAM_NAME, token)
                    .param(Messenger.CHALLENGE_REQUEST_PARAM_NAME, challenge))

        then:
            response
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("Couldn't verify web hook."))
    }

}
