package eu.codeloop.thehub

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

import com.github.tomakehurst.wiremock.core.Options.DYNAMIC_PORT
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@RunWith(SpringRunner::class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = DYNAMIC_PORT)
abstract class IntegrationTest {

    @Autowired
    protected lateinit var mvc: MockMvc
}
