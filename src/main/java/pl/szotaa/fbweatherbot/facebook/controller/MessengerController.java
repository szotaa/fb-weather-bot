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
import pl.szotaa.fbweatherbot.facebook.service.MessengerService;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MessengerController {

    private final MessengerService messengerService;

    @GetMapping
    public ResponseEntity<String> verify (
            @RequestParam(Messenger.MODE_REQUEST_PARAM_NAME) String mode,
            @RequestParam(Messenger.VERIFY_TOKEN_REQUEST_PARAM_NAME) String token,
            @RequestParam(Messenger.CHALLENGE_REQUEST_PARAM_NAME) String challenge) throws MessengerVerificationException {

        log.info("controller received verification with mode: " + mode + " token: " + token + " challenge: " + challenge);
        this.messengerService.verifyWebHook(mode, token);
        return ResponseEntity.ok(challenge);
    }

    @PostMapping
    public ResponseEntity<Void> handleMessage (
            @RequestBody String payload,
            @RequestHeader(Messenger.SIGNATURE_HEADER_NAME) String signature) throws MessengerVerificationException {

        log.info("controller received payload: " + payload + " with signature: " + signature);
        this.messengerService.handleMessage(payload, signature);
        return ResponseEntity.ok().build();
    }
}
