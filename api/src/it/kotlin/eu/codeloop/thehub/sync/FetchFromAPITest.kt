package eu.codeloop.thehub.sync

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.http.UniformDistribution
import eu.codeloop.thehub.IntegrationTest
import eu.codeloop.thehub.base.DatabaseSetup
import eu.codeloop.thehub.base.DatabaseSetupOperations
import org.awaitility.Awaitility.await
import org.awaitility.core.ConditionFactory
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType.*
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.concurrent.TimeUnit.MILLISECONDS
import wiremock.org.apache.http.HttpHeaders.CONTENT_TYPE

class FetchFromAPITest : IntegrationTest() {

    @Autowired
    private lateinit var databaseSetup: DatabaseSetup

    @Value("\${thehub.sync.domains}")
    private lateinit var syncDomains: String

    private val lowerDelay = 200
    private val upperDelay = 2000
    private val uniformDistribution = UniformDistribution(lowerDelay, upperDelay)

    @Test
    fun `it should fetch jobs from external api and return sorted list`() {
        // given
        val awaitTime = ((upperDelay * 3) + (10 * 1000)).toLong() //download + delay after that
        databaseSetup.prepareDatabase(
            DatabaseSetupOperations.deleteAll()
        )
        removeAllMappings()
        addJsonMapping("/api/jobs?page=1", "body-api-jobs-yH61E.json")
        addHtmlMapping("/jobs/chief-financial-officer-cfo-1", "body-jobs-chief-financial-officer-cfo-1-kgwWj.txt")
        addHtmlMapping("/jobs/chief-marketing-officer-cmo-6", "body-jobs-chief-marketing-officer-cmo-6-8G7JB.txt")
        val syncRequest = MockMvcRequestBuilders
            .post("/api/jobs/sync")
            .accept(ALL_VALUE)
        val jobsRequest = MockMvcRequestBuilders
            .get("/api/jobs")
            .accept(APPLICATION_JSON_VALUE)

        // when
        val syncResult = mvc.perform(syncRequest)
        val jobsResult = await().atMost(awaitTime, MILLISECONDS).untilRequest({ mvc.perform(jobsRequest) }) {
            response -> response.andExpect(jsonPath("$.totalElements").value(2))
        }

        // then
        syncResult
            .andExpect(status().isNoContent)
        jobsResult
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.totalElements").value(2))
            // TODO - więcej asercji
    }

    private fun addJsonMapping(url: String, fileName: String) {
        stubFor(get(urlPathEqualTo(url)).willReturn(
            aResponse()
                .withStatus(200)
                .withRandomDelay(uniformDistribution)
                .withHeader(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
                .withBodyFile(fileName)
        ))
    }

    private fun addHtmlMapping(url: String, fileName: String) {
        stubFor(get(urlPathEqualTo(url)).willReturn(
            aResponse()
                .withStatus(200)
                .withRandomDelay(uniformDistribution)
                .withHeader("Content-Type", TEXT_HTML_VALUE)
                .withBodyFile(fileName)
        ))
    }

    fun ConditionFactory.untilRequest(resultProvider: () -> ResultActions, resultChecker: (ResultActions) -> Unit): ResultActions {
        return this.until(resultProvider) { r ->
            try {
                resultChecker(r)
                true
            } catch (e: java.lang.AssertionError) {
                false
            }
        }
    }
}
