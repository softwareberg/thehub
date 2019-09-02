package eu.codeloop.thehub

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.common.ClasspathFileSource
import com.github.tomakehurst.wiremock.common.Slf4jNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import wiremock.org.apache.http.HttpHeaders

@Configuration
class WireMockConfig {

    @Bean
    fun wireMockConfiguration(@Value("\${wiremock.server.port}") port: Int): WireMockConfiguration {
        return WireMockConfiguration.wireMockConfig()
            .port(port)
            .fileSource(ClasspathFileSource("."))
            .jettyHeaderBufferSize(32768)
            .notifier(Slf4jNotifier(true))
    }

}

fun addJsonMapping(url: String, fileName: String) {
    WireMock.stubFor(WireMock.get(url).willReturn(
            WireMock.aResponse()
                    .withStatus(200)
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .withBodyFile(fileName)
    ))
}

fun addHtmlMapping(url: String, fileName: String) {
    WireMock.stubFor(WireMock.get(url).willReturn(
            WireMock.aResponse()
                    .withStatus(200)
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                    .withBodyFile(fileName)
    ))
}
