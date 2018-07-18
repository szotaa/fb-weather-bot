package pl.szotaa.fbweatherbot.config

import spock.lang.Specification

class Messenger4JConfigTest extends Specification {

    def messenger4JConfig = new Messenger4JConfig()

    def "Correct Messenger object created"() {
        given:

            def pageAccessToken = "pageAccessToken"
            def appSecret = "appSecret"
            def verifyToken = "verifyToken"
        when:

            def messenger = messenger4JConfig.getMessenger(
                    pageAccessToken,
                    appSecret,
                    verifyToken)
        then:

            messenger.pageAccessToken == pageAccessToken
            messenger.appSecret == appSecret
            messenger.verifyToken == verifyToken
    }
}
