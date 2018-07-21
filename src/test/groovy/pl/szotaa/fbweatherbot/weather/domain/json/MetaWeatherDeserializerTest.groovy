package pl.szotaa.fbweatherbot.weather.domain.json

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import pl.szotaa.fbweatherbot.weather.domain.Weather
import spock.lang.Specification

import java.time.LocalDate

@JsonTest
class MetaWeatherDeserializerTest extends Specification {

    @Autowired
    JacksonTester<Weather> jacksonTester

    def "Weather is properly deserialized from JSON"() {
        given:
            def json = "{\n" +
                    "   \"consolidated_weather\": [\n" +
                    "      {\n" +
                    "         \"weather_state_name\": \"Heavy Cloud\",\n" +
                    "         \"weather_state_abbr\": \"hc\",\n" +
                    "         \"applicable_date\": \"2018-07-21\",\n" +
                    "         \"min_temp\": 17.6575,\n" +
                    "         \"max_temp\": 27.6025,\n" +
                    "         \"the_temp\": 25.93,\n" +
                    "         \"air_pressure\": 1017.03,\n" +
                    "         \"humidity\": 58\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"weather_state_name\": \"Heavy Cloud\",\n" +
                    "         \"weather_state_abbr\": \"hc\",\n" +
                    "         \"applicable_date\": \"2018-07-22\",\n" +
                    "         \"min_temp\": 18.4425,\n" +
                    "         \"max_temp\": 26.61,\n" +
                    "         \"the_temp\": 25.615,\n" +
                    "         \"air_pressure\": 1019.345,\n" +
                    "         \"humidity\": 53\n" +
                    "      }\n" +
                    "   ],\n" +
                    "   \"parent\": {\n" +
                    "      \"title\": \"England\",\n" +
                    "      \"location_type\": \"Region / State / Province\",\n" +
                    "      \"woeid\": 24554868,\n" +
                    "      \"latt_long\": \"52.883560,-1.974060\"\n" +
                    "   },\n" +
                    "   \"title\": \"London\",\n" +
                    "   \"location_type\": \"City\",\n" +
                    "   \"woeid\": 44418,\n" +
                    "   \"latt_long\": \"51.506321,-0.12714\",\n" +
                    "   \"timezone\": \"Europe/London\"\n" +
                    "}"
        when:
            def weather = jacksonTester.parseObject(json)

        then:
            weather.getForecasts().size() == 2
            weather.location.equals("London")
            weather.parentLocation.equals("England")
            def forecast1 = weather.getForecasts().get(0)
            forecast1.getApplicableDate().equals(LocalDate.of(2018, 7, 21))
            forecast1.getWeatherState().equals("Heavy Cloud")
            forecast1.getWeatherStateAbbr().equals("hc")
            forecast1.getMaxTemp().equals(27.6025d)
            forecast1.getMinTemp().equals(17.6575d)
            forecast1.getTemp().equals(25.93d)
            forecast1.getPressure().equals(1017.03d)
            forecast1.getHumidity().equals(58d)
    }
}
