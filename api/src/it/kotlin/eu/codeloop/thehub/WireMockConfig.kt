package eu.codeloop.thehub

import com.github.tomakehurst.wiremock.common.ClasspathFileSource
import com.github.tomakehurst.wiremock.common.Slf4jNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

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
