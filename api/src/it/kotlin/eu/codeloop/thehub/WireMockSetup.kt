package eu.codeloop.thehub

import com.github.tomakehurst.wiremock.client.WireMock
import org.springframework.http.MediaType
import wiremock.org.apache.http.HttpHeaders

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
