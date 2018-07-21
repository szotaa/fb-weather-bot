package pl.szotaa.fbweatherbot.facebook.controller;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerVerificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.szotaa.fbweatherbot.facebook.communication.ReceiveService;
import pl.szotaa.fbweatherbot.facebook.verification.WebHookVerificationService;

/**
 * Handles all messages sent to our application through Facebook Messenger.
 * @see <a href="https://developers.facebook.com/docs/graph-api/webhooks/">Facebook WebHook documentation</a>
 *
 * @author szotaa
 */

@Slf4j
@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class WebHookController {

    private final ReceiveService receiveService;
    private final WebHookVerificationService verificationService;

    /**
     * WebHook verification endpoint.
     * @see WebHookVerificationService
     */

    @GetMapping
    public ResponseEntity<String> verifyWebHook (
            @RequestParam(Messenger.MODE_REQUEST_PARAM_NAME) String mode,
            @RequestParam(Messenger.VERIFY_TOKEN_REQUEST_PARAM_NAME) String token,
            @RequestParam(Messenger.CHALLENGE_REQUEST_PARAM_NAME) String challenge) throws MessengerVerificationException {

        log.info("controller received verification with mode: " + mode + " token: " + token + " challenge: " + challenge);
        this.verificationService.verifyWebHook(mode, token);
        return ResponseEntity.ok(challenge);
    }

    /**
     * Incoming payload handler method.
     * @see ReceiveService
     */

    @PostMapping
    public ResponseEntity<Void> handlePayload (
            @RequestHeader(Messenger.SIGNATURE_HEADER_NAME) String signature,
            @RequestBody String payload ) throws MessengerVerificationException {

        log.info("controller received payload: " + payload + " with signature: " + signature);
        this.receiveService.handleReceivedPayload(payload, signature);
        return ResponseEntity.ok().build();
    }
}
