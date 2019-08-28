package eu.codeloop.thehub.sync

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.http.UniformDistribution
import eu.codeloop.thehub.IntegrationTest
import eu.codeloop.thehub.base.DatabaseSetup
import eu.codeloop.thehub.base.DatabaseSetupOperations
import org.awaitility.Awaitility.await
import org.junit.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType.ALL_VALUE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.concurrent.TimeUnit.MILLISECONDS

class FetchFromAPITest : IntegrationTest() {

    @Autowired
    private lateinit var databaseSetup: DatabaseSetup

    @Value("\${thehub.sync.domains}")
    private lateinit var syncDomains: String

    private val log = LoggerFactory.getLogger(FetchFromAPITest::class.java)

    @Test
    fun `it should fetch jobs from external api`() {
        log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX") //TODO - does not print :(

        // given
        val lowerDelay = 200
        val upperDealy = 2000
        val awaitTime = ((upperDealy * 3) + (5 * 1000)).toLong() //download + delay after that
        databaseSetup.prepareDatabase(
            DatabaseSetupOperations.deleteAll()
        )
        stubFor(get(urlPathEqualTo("/jobs")).willReturn(
            aResponse()
                .withStatus(200)
                .withRandomDelay(UniformDistribution(lowerDelay, upperDealy))
                .withHeader("Content-Type", "application/json; charset=utf-8")
                .withBodyFile("body-api-jobs-yH61E.json") //TODO - is path OK?
        ))
        stubFor(get(urlPathEqualTo("/jobs/chief-financial-officer-cfo-1")).willReturn(
            aResponse()
                .withStatus(200)
                .withRandomDelay(UniformDistribution(lowerDelay, upperDealy))
                .withHeader("Content-Type", "application/json; charset=utf-8")
                .withBodyFile("body-jobs-chief-financial-officer-cfo-1-kgwWj.txt") //TODO
        ))
        stubFor(get(urlPathEqualTo("/jobs/chief-marketing-officer-cmo-6")).willReturn(
            aResponse()
                .withStatus(200)
                .withRandomDelay(UniformDistribution(lowerDelay, upperDealy))
                .withHeader("Content-Type", "application/json; charset=utf-8")
                .withBodyFile("body-jobs-chief-marketing-officer-cmo-6-8G7JB.txt") //TODO
        ))
        val syncRequest = MockMvcRequestBuilders
            .post("/api/jobs/sync")
            .accept(ALL_VALUE)
        val jobsRequest = MockMvcRequestBuilders
            .get("/api/jobs")
            .accept(APPLICATION_JSON_VALUE)

        // when
        val syncResult = mvc.perform(syncRequest)
        await().atMost(awaitTime, MILLISECONDS).untilAsserted {
            mvc.perform(jobsRequest)
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.totalElements").value(2)) // TODO - works with 0 :)
        }
        val jobsResult = mvc.perform(jobsRequest)

        // then
        syncResult
            .andExpect(status().isNoContent)
        jobsResult
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.totalElements").value(2))
            // TODO - więcej asercji
    }
}
