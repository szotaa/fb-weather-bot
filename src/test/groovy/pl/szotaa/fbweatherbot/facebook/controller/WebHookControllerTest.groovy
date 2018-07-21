package pl.szotaa.fbweatherbot.facebook.controller

import com.github.messenger4j.Messenger
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.szotaa.fbweatherbot.facebook.communication.ReceiveService
import pl.szotaa.fbweatherbot.facebook.verification.WebHookVerificationService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class WebHookControllerTest extends Specification {

    def receiveService = Mock(ReceiveService)
    def webHookVerificationService = Mock(WebHookVerificationService)
    def messengerController = new WebHookController(receiveService, webHookVerificationService)
    def mockMvc = MockMvcBuilders.standaloneSetup(messengerController).build()

    def 'Verifying web hook with correct parameters should return the same string as passed challenge string'() {
        given:
            def mode = "subscribe"
            def token = "token"
            def challenge = "challenge"

        when:
            def response = mockMvc.perform(get("/webhook")
                                .param(Messenger.MODE_REQUEST_PARAM_NAME, mode)
                                .param(Messenger.VERIFY_TOKEN_REQUEST_PARAM_NAME, token)
                                .param(Messenger.CHALLENGE_REQUEST_PARAM_NAME, challenge))

        then:
            1 * webHookVerificationService.verifyWebHook(!null as String, !null as String)

            response
                .andExpect(status().isOk())
                .andExpect(content().string(challenge))
    }

    def 'Handling payload with correct parameters should return 200 ok response status.'(){
        given:
            def payload = "payload"
            def signature = "signature"

        when:
            def response = mockMvc.perform(post("/webhook")
                .header(Messenger.SIGNATURE_HEADER_NAME, signature)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(payload))

        then:
            response
                .andExpect(status().isOk())
    }
}
