package pl.szotaa.fbweatherbot.facebook.communication;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szotaa.fbweatherbot.facebook.domain.Response;

/**
 * Sends processed response back to user who requested it.
 *
 * @author szotaa
 */

@Service
@RequiredArgsConstructor
public class ResponseService {

    public void respond(Response response, String recipentId){

    }
}
