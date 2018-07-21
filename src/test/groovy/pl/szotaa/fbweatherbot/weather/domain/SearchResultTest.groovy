package pl.szotaa.fbweatherbot.weather.domain

import spock.lang.Specification

class SearchResultTest extends Specification {

    def "Equal SearchResult object should have equal hash codes"(){
        given:
            def warsaw = new SearchResult("Warsaw", 001)
            def warsaw1 = new SearchResult("Warsaw", 001)
        expect:
            warsaw.equals(warsaw1)
            warsaw.hashCode() == warsaw1.hashCode()
    }

    def "Equals method should behave properly"() {
        given:
            def warsaw = new SearchResult("Warsaw", 001)
            def warsaw1 = new SearchResult("Warsaw", 001)
            def cracow = new SearchResult("Cracow", 002)
        expect:
            warsaw.equals(warsaw)
            warsaw.equals(warsaw1)
            warsaw1.equals(warsaw)
            !warsaw1.equals(cracow)
            !cracow.equals(null)
    }

    def "HashCode method should behave properly"(){
        given:
            def warsaw = new SearchResult("Warsaw", 001)
            def warsaw1 = new SearchResult("Warsaw", 001)
            def cracow = new SearchResult("Cracow", 002)
        expect:
            warsaw.hashCode() == warsaw.hashCode()
            warsaw.hashCode() == warsaw1.hashCode()
            warsaw.hashCode() != cracow.hashCode()
    }
}
