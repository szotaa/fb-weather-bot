package pl.szotaa.fbweatherbot.config

import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class RestTemplateConfigTest extends Specification {

    def restTemplateConfig = new RestTemplateConfig()

    def "Correct RestTemplate returned"(){
        when:
            def restTemplate = restTemplateConfig.getRestTemplate()
        then:
            restTemplate != null
            restTemplate instanceof RestTemplate
    }
}
