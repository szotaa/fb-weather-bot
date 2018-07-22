package pl.szotaa.fbweatherbot.facebook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnknownRequest implements Request {

    private String parameter;
}
