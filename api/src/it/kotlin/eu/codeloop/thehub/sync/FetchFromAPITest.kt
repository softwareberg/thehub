package eu.codeloop.thehub.sync

import com.github.tomakehurst.wiremock.client.WireMock.*
import eu.codeloop.thehub.IntegrationTest
import eu.codeloop.thehub.base.DatabaseSetup
import eu.codeloop.thehub.base.DatabaseSetupOperations
import org.hamcrest.Matchers.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType.ALL_VALUE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class FetchFromAPITest: IntegrationTest() {

    @Autowired
    private lateinit var databaseSetup: DatabaseSetup

    @Value("\${server.port}")
    private var serverPort: Int = 8080

    @Test
    @Throws(Exception::class)
    fun shouldCheckEndpoint() {
        // 1. mieć 3 fałszywe endpointy udające API
        // given
        databaseSetup.prepareDatabase(
                DatabaseSetupOperations.deleteAll()
        )
        stubFor(get(urlMatching("/api/jobs/sync"))
                .willReturn(aResponse().proxiedFrom("http://localhost:${serverPort}/api/jobs/sync")));
        val syncRequest = MockMvcRequestBuilders
                .post("/api/jobs/sync")
                .accept(ALL_VALUE)
        val jobsRequest = MockMvcRequestBuilders
                .get("/api/jobs")
                .accept(APPLICATION_JSON_VALUE)

        // 2. zrobić request do naszego api
        // when
        val syncResult = mvc.perform(syncRequest)

        //awaiti... coś tam

        val jobsResult = mvc.perform(jobsRequest)

        // 3. wyciągnąć wyniki
        // then
        syncResult
                .andExpect(status().isOk)
        jobsResult
                .andExpect(status().isOk)
    }
}
