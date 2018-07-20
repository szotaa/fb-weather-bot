package pl.szotaa.fbweatherbot.facebook.verification;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Verifies token received from Facebook for security purposes.
 *
 * @author szotaa
 */

@Service
@RequiredArgsConstructor
public class WebHookVerificationService {

    private final Messenger messenger;

    public void verifyWebHook(String mode, String token) throws MessengerVerificationException {
        this.messenger.verifyWebhook(mode, token);
    }
}
