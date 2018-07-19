package pl.szotaa.fbweatherbot.facebook.controller;

import com.github.messenger4j.exception.MessengerVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MessengerControllerAdvice {

    @ExceptionHandler(MessengerVerificationException.class)
    public ResponseEntity<String> handleMessengerVerificationException(MessengerVerificationException e) {
        log.warn("MessengerVerificationException caught: " + e.getMessage());
        return ResponseEntity.badRequest().body("Couldn't verify web hook.");
    }
}
