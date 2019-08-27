package eu.codeloop.thehub.sync

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import eu.codeloop.thehub.IntegrationTest
import eu.codeloop.thehub.base.DatabaseSetup
import eu.codeloop.thehub.base.DatabaseSetupOperations
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType.ALL_VALUE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class FetchFromAPITest : IntegrationTest() {

    @Autowired
    private lateinit var databaseSetup: DatabaseSetup

    @Value("\${thehub.sync.domains}")
    private lateinit var syncDomains: String

    @Test
    fun shouldCheckEndpoint() {
        // given
        databaseSetup.prepareDatabase(
            DatabaseSetupOperations.deleteAll()
        )
        stubFor(get("/api/jobs?page=1").willReturn(aResponse().withBodyFile("/jobs/jobs.json")))
        val syncRequest = MockMvcRequestBuilders
            .post("/api/jobs/sync")
            .accept(ALL_VALUE)
        val jobsRequest = MockMvcRequestBuilders
            .get("/api/jobs")
            .accept(APPLICATION_JSON_VALUE)

        // when
        val syncResult = mvc.perform(syncRequest)

        // awaitility
        val jobsResult = mvc.perform(jobsRequest)


        // 3. wyciągnąć wyniki
        // then
        syncResult
            .andExpect(status().isNoContent)
        jobsResult
            .andExpect(status().isOk)
    }
}
