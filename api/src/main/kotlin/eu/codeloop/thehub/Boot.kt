package eu.codeloop.thehub

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@ConditionalOnProperty(
    prefix = "thehub.scheduling", name = ["enable"], havingValue = "true", matchIfMissing = true
)
@Configuration
@EnableScheduling
class SchedulingConfiguration

@Configuration
@EnableJpaRepositories
class JpaConfiguration

@SpringBootApplication
class Boot

@SuppressWarnings("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<Boot>(*args)
}
