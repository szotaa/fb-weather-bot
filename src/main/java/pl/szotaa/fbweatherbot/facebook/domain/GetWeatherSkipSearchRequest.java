package pl.szotaa.fbweatherbot.facebook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Weather fetch request.
 *
 * @author szotaa
 */

@Getter
@AllArgsConstructor
public final class GetWeatherSkipSearchRequest implements Request {

    private String parameter;
}
