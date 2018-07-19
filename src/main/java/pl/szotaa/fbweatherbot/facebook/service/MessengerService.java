package pl.szotaa.fbweatherbot.facebook.service;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessengerService {

    private final Messenger messenger;

    public void verifyWebHook(String mode, String token) throws MessengerVerificationException {
        this.messenger.verifyWebhook(mode, token);
    }
}
