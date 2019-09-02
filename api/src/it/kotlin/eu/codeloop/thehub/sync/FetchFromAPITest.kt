package eu.codeloop.thehub.sync

import com.github.tomakehurst.wiremock.client.WireMock.*
import eu.codeloop.thehub.IntegrationTest
import eu.codeloop.thehub.base.DatabaseSetup
import eu.codeloop.thehub.base.DatabaseSetupOperations
import eu.codeloop.thehub.addHtmlMapping
import eu.codeloop.thehub.addJsonMapping
import eu.codeloop.thehub.untilRequest
import org.awaitility.Awaitility.await
import org.junit.Test
import org.hamcrest.Matchers.containsString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.concurrent.TimeUnit.SECONDS

class FetchFromAPITest : IntegrationTest() {

    @Autowired
    private lateinit var databaseSetup: DatabaseSetup

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
            .andExpect(jsonPath("$.data[${0}].jobId", containsString("marketing")))
            .andExpect(jsonPath("$.data[${0}].title", containsString("Marketing")))
            .andExpect(jsonPath("$.data[${0}].description", containsString("everyday heroes")))
            .andExpect(jsonPath("$.data[${0}].hasStar").value(false))
            .andExpect(jsonPath("$.data[${0}].keywords").isArray)
            .andExpect(jsonPath("$.data[${0}].approvedAt", containsString("2019")))
            .andExpect(jsonPath("$.data[${0}].logo", containsString("5b85123423003ae417ed233e/logo_upload-bf606eb14a4ac7f8610aa6bb92968ea0.jpg")))
            .andExpect(jsonPath("$.data[${0}].poster", containsString("/files/5b85123423003ae417ed233e/coverImage_upload-e46fd5472487b6efb18316e80a3dd745.jpg")))
            .andExpect(jsonPath("$.data[${1}].jobId", containsString("financial")))
            .andExpect(jsonPath("$.data[${1}].title", containsString("Financial")))
            .andExpect(jsonPath("$.data[${1}].description", containsString("everyday heroes")))
            .andExpect(jsonPath("$.data[${1}].hasStar").value(false))
            .andExpect(jsonPath("$.data[${1}].keywords").isArray)
            .andExpect(jsonPath("$.data[${1}].approvedAt", containsString("2000")))
            .andExpect(jsonPath("$.data[${1}].logo", containsString("5b85123423003ae417ed233e/logo_upload-bf606eb14a4ac7f8610aa6bb92968ea0.jpg")))
            .andExpect(jsonPath("$.data[${1}].poster", containsString("/images/default_cover.jpg")))
    }
}
