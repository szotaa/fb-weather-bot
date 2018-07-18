package pl.szotaa.fbweatherbot.controller;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerVerificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MessengerController {

    private final Messenger messenger;

    @GetMapping
    public ResponseEntity<String> verify (
            @RequestParam(Messenger.MODE_REQUEST_PARAM_NAME) String mode,
            @RequestParam(Messenger.VERIFY_TOKEN_REQUEST_PARAM_NAME) String verifyToken,
            @RequestParam(Messenger.CHALLENGE_REQUEST_PARAM_NAME) String challenge
    ){
        log.debug(String.format("Received verification request: mode - %s, token - %s, challenge - %s", mode, verifyToken, challenge));
        try {
            this.messenger.verifyWebhook(mode, verifyToken);
            return ResponseEntity.ok(challenge);
        } catch (MessengerVerificationException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
