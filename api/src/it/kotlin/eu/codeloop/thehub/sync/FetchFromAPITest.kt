package eu.codeloop.thehub.sync

import com.github.tomakehurst.wiremock.client.WireMock.*
import eu.codeloop.thehub.IntegrationTest
import eu.codeloop.thehub.base.DatabaseSetup
import eu.codeloop.thehub.base.DatabaseSetupOperations
import org.awaitility.Awaitility.await
import org.awaitility.core.ConditionFactory
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.*
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import wiremock.org.apache.http.HttpHeaders.CONTENT_TYPE
import java.util.concurrent.TimeUnit.SECONDS

class FetchFromAPITest : IntegrationTest() {

    @Autowired
    private lateinit var databaseSetup: DatabaseSetup

    @Test
    fun `it should fetch jobs from external api`() {
        // given
        databaseSetup.prepareDatabase(
            DatabaseSetupOperations.deleteAll()
        )
        removeAllMappings()
        addJsonMapping("/api/jobs?page=1", "body-api-jobs-yH61E.json")
        addHtmlMapping("/jobs/chief-financial-officer-cfo-1", "body-jobs-chief-financial-officer-cfo-1-kgwWj.txt")
        addHtmlMapping("/jobs/chief-marketing-officer-cmo-6", "body-jobs-chief-marketing-officer-cmo-6-8G7JB.txt")

        val syncRequest = MockMvcRequestBuilders
            .post("/api/jobs/sync")
            .accept(APPLICATION_JSON_VALUE)
        val jobsRequest = MockMvcRequestBuilders
            .get("/api/jobs")
            .accept(APPLICATION_JSON_VALUE)

        // when
        val syncResult = mvc.perform(syncRequest)
        val jobsResult = await().atMost(10, SECONDS).untilRequest({ mvc.perform(jobsRequest) }) { response ->
            response.andExpect(jsonPath("$.totalElements").value(2))
        }

        // then
        syncResult
            .andExpect(status().isNoContent)
        jobsResult
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.totalElements").value(2))
    }

    private fun addJsonMapping(url: String, fileName: String) {
        stubFor(get(url).willReturn(
            aResponse()
                .withStatus(200)
                .withHeader(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
                .withBodyFile(fileName)
        ))
    }

    private fun addHtmlMapping(url: String, fileName: String) {
        stubFor(get(url).willReturn(
            aResponse()
                .withStatus(200)
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
