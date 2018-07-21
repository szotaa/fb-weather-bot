package pl.szotaa.fbweatherbot.facebook.controller

import com.github.messenger4j.Messenger
import com.github.messenger4j.exception.MessengerVerificationException
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.szotaa.fbweatherbot.facebook.communication.ReceiveService
import pl.szotaa.fbweatherbot.facebook.verification.WebHookVerificationService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class WebHookControllerAdviceTest extends Specification {

    def receiveService = Mock(ReceiveService)
    def webHookVerificationService = Mock(WebHookVerificationService)
    def messengerController = new WebHookController(receiveService, webHookVerificationService)
    def messengerControllerAdvice = new WebHookControllerAdvice()
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
            webHookVerificationService.verifyWebHook(_  as String, _ as String) >> {throw new MessengerVerificationException("")}
            def response = mockMvc.perform(get("/webhook")
                    .param(Messenger.MODE_REQUEST_PARAM_NAME, mode)
                    .param(Messenger.VERIFY_TOKEN_REQUEST_PARAM_NAME, token)
                    .param(Messenger.CHALLENGE_REQUEST_PARAM_NAME, challenge))

        then:
            response
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("Couldn't verify web hook."))
    }

    def 'Handling payload with incorrect parameters should return 400 bad request response status.'(){
        given:
            def payload = "payload"
            def signature = "signature"

        when:
            receiveService.handleReceivedPayload(_  as String, _ as String) >> {throw new MessengerVerificationException("")}
            def response = mockMvc.perform(post("/webhook")
                    .header(Messenger.SIGNATURE_HEADER_NAME, signature)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(payload))

        then:
            response
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Couldn't verify web hook."))
    }
}
