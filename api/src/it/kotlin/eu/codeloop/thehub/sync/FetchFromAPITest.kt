package eu.codeloop.thehub.sync

import com.github.tomakehurst.wiremock.client.WireMock.*
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
import java.util.concurrent.TimeUnit.SECONDS
import wiremock.org.apache.http.HttpHeaders.CONTENT_TYPE

class FetchFromAPITest : IntegrationTest() {

    @Autowired
    private lateinit var databaseSetup: DatabaseSetup

    @Value("\${thehub.sync.domains}")
    private lateinit var syncDomains: String

    @Test
    fun `it should fetch jobs from external api and return sorted list`() {
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
            .accept(ALL_VALUE)
        val jobsRequest = MockMvcRequestBuilders
            .get("/api/jobs")
            .accept(APPLICATION_JSON_VALUE)

        // when
        val syncResult = mvc.perform(syncRequest)
        val jobsResult = await().atMost(10, SECONDS).untilRequest({ mvc.perform(jobsRequest) }) {
            response -> response.andExpect(jsonPath("$.totalElements").value(2))
        }

        // then
        val chiefMarketingIndex = 0
        val chiefFinancialIndex = 1
        syncResult
            .andExpect(status().isNoContent)
        jobsResult
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.totalPages").value(1))
            .andExpect(jsonPath("$.number").value(1))
            .andExpect(jsonPath("$.size").isNumber)
            .andExpect(jsonPath("$.first").value(true))
            .andExpect(jsonPath("$.last").value(true))
            .andExpect(jsonPath("$.totalElements").value(2))
            .andExpect(jsonPath("$.numberOfElements").value(2))
            .andExpect(jsonPath("$.data[${chiefMarketingIndex}].jobId").value("chief-marketing-officer-cmo-6"))
            .andExpect(jsonPath("$.data[${chiefMarketingIndex}].title").value("Chief Marketing Officer (CMO)"))
            .andExpect(jsonPath("$.data[${chiefMarketingIndex}].description").isString) //TODO
            .andExpect(jsonPath("$.data[${chiefMarketingIndex}].hasStar").value(false))
            .andExpect(jsonPath("$.data[${chiefMarketingIndex}].keywords").isArray) //TODO
            .andExpect(jsonPath("$.data[${chiefMarketingIndex}].approvedAt").isString) //TODO
            .andExpect(jsonPath("$.data[${chiefMarketingIndex}].logo").isString) //TODO
            .andExpect(jsonPath("$.data[${chiefMarketingIndex}].poster").isString) //TODO

            .andExpect(jsonPath("$.data[${chiefFinancialIndex}].jobId").value("chief-financial-officer-cfo-1"))
            .andExpect(jsonPath("$.data[${chiefFinancialIndex}].title").value("Chief Financial Officer (CFO)"))
            .andExpect(jsonPath("$.data[${chiefFinancialIndex}].description").isString) //TODO
            .andExpect(jsonPath("$.data[${chiefFinancialIndex}].hasStar").value(false))
            .andExpect(jsonPath("$.data[${chiefFinancialIndex}].keywords").isArray) //TODO
            .andExpect(jsonPath("$.data[${chiefFinancialIndex}].approvedAt").isString)
            .andExpect(jsonPath("$.data[${chiefFinancialIndex}].logo").isString) //TODO
            .andExpect(jsonPath("$.data[${chiefFinancialIndex}].poster").isString) //TODO
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
