package pl.szotaa.fbweatherbot.weather.domain

import spock.lang.Specification

import java.time.LocalDate

class ForecastTest extends Specification {

    def "CompareTo method should behave properly"() {
        given:
            def forecastYesterday = Forecast.builder()
                        .applicableDate(LocalDate.now().minusDays(1))
                        .build()

            def forecastToday = Forecast.builder()
                    .applicableDate(LocalDate.now())
                    .build()

            def forecastTomorrow = Forecast.builder()
                    .applicableDate(LocalDate.now().plusDays(1))
                    .build()

        expect:
            forecastToday.compareTo(forecastToday) == 0
            forecastToday.compareTo(forecastYesterday) > 0
            forecastToday.compareTo(forecastTomorrow) < 0
    }
}
