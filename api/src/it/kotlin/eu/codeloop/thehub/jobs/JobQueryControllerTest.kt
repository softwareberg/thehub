package eu.codeloop.thehub.jobs

import eu.codeloop.thehub.IntegrationTest
import org.hamcrest.Matchers.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.junit.Test
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class JobQueryControllerTest: IntegrationTest() {

    @Test
    @Throws(Exception::class)
    fun shouldCheckEndpoint() {
        // given
        val request = MockMvcRequestBuilders
                .get("/jobs")
                .accept(APPLICATION_JSON_VALUE)

        // when
        val result = mvc.perform(request)

        // then
        result
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.first", equalTo(false)))
    }
}
