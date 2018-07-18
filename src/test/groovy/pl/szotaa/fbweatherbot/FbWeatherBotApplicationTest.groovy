package pl.szotaa.fbweatherbot

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class FbWeatherBotApplicationTest extends Specification {

    def "Application should boot"() {
        expect:
            true
    }
}
