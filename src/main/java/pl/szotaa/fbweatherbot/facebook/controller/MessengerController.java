package pl.szotaa.fbweatherbot.facebook.controller;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.szotaa.fbweatherbot.facebook.service.MessengerService;

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

        this.messengerService.verifyWebHook(mode, token);
        return ResponseEntity.ok(challenge);
    }
}
