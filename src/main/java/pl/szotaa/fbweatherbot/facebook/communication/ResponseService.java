package pl.szotaa.fbweatherbot.facebook.communication;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.send.MessagePayload;
import com.github.messenger4j.send.MessagingType;
import com.github.messenger4j.send.message.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.szotaa.fbweatherbot.facebook.domain.GetWeatherSkipSearchRequest;
import pl.szotaa.fbweatherbot.facebook.domain.Request;
import pl.szotaa.fbweatherbot.facebook.domain.UnknownRequest;
import pl.szotaa.fbweatherbot.weather.domain.Weather;
import pl.szotaa.fbweatherbot.weather.exception.LocationNotFoundException;
import pl.szotaa.fbweatherbot.weather.exception.NoForecastException;
import pl.szotaa.fbweatherbot.weather.service.WeatherClientService;

/**
 * Builds and sends response to users Request.
 *
 * @author szotaa
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ResponseService {

    private final Messenger messenger;
    private final WeatherClientService weatherClientService;

    public void respond(Request request, String recipientId){
        if(request instanceof GetWeatherSkipSearchRequest){
            this.respondWithTextWeatherSkipSearch(request.getParameter(), recipientId);
        } else if (request instanceof UnknownRequest) {
            this.respondWithRequestUnknown(request.getParameter(), recipientId);
        }
    }

    private void respondWithTextWeatherSkipSearch(String location, String recipientId) {

        String text;

        try {
            Weather weather = weatherClientService.getWeatherSkipSearch(location);
            text = weather.toString();

        } catch (LocationNotFoundException e) {
            text = location + " not found";

        } catch (NoForecastException e) {
            text = "No forecast for location: " + location;
        }

        TextMessage textMessage = TextMessage.create(text);
        MessagePayload messagePayload = MessagePayload.create(recipientId, MessagingType.RESPONSE, textMessage);
        try {
            this.messenger.send(messagePayload);
        } catch (MessengerApiException | MessengerIOException e) {
            log.info(e.getMessage());
        }
    }

    private void respondWithRequestUnknown(String cause, String recipentId){
        TextMessage textMessage = TextMessage.create("Unknown command: " + cause);
        MessagePayload messagePayload = MessagePayload.create(recipentId, MessagingType.RESPONSE, textMessage);
        try {
            this.messenger.send(messagePayload);
        } catch (MessengerApiException | MessengerIOException e) {
            log.info(e.getMessage());
        }
    }
}
