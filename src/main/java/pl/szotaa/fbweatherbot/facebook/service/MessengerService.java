package pl.szotaa.fbweatherbot.facebook.service;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.exception.MessengerVerificationException;
import com.github.messenger4j.send.MessagePayload;
import com.github.messenger4j.send.MessagingType;
import com.github.messenger4j.send.message.TextMessage;
import com.github.messenger4j.webhook.event.TextMessageEvent;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessengerService {

    private final Messenger messenger;

    public void verifyWebHook(String mode, String token) throws MessengerVerificationException {
        this.messenger.verifyWebhook(mode, token);
    }

    public void handleMessage(String payload, String signature) throws MessengerVerificationException {
        Optional<String> signatureOptional = Optional.ofNullable(signature);
        this.messenger.onReceiveEvents(payload, signatureOptional, event -> {
            if(event.isTextMessageEvent()){
                this.processTextMessageEvent(event.asTextMessageEvent());
            }
            else {
                throw new RuntimeException("message type not yet supported"); //TODO: implement this
            }
        });
    }

    private void processTextMessageEvent(TextMessageEvent event) {
        log.info("Received text message: " + event.toString());
        TextMessage message = TextMessage.create("elo");
        try {
            messenger.send(MessagePayload.create(event.senderId(), MessagingType.RESPONSE, message));
        } catch (MessengerApiException | MessengerIOException e) {
            log.error("something went wrong: " + e.getMessage());
        }
    }
}
