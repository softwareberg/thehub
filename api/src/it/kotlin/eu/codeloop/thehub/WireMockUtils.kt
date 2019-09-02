package eu.codeloop.thehub

import com.github.tomakehurst.wiremock.client.WireMock
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.http.MediaType.TEXT_HTML_VALUE
import wiremock.org.apache.http.HttpHeaders.CONTENT_TYPE

fun addJsonMapping(url: String, fileName: String) {
    WireMock.stubFor(WireMock.get(url).willReturn(
        WireMock.aResponse()
            .withStatus(200)
            .withHeader(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
            .withBodyFile(fileName)
    ))
}

fun addHtmlMapping(url: String, fileName: String) {
    WireMock.stubFor(WireMock.get(url).willReturn(
        WireMock.aResponse()
            .withStatus(200)
            .withHeader(CONTENT_TYPE, TEXT_HTML_VALUE)
            .withBodyFile(fileName)
    ))
}
