package eu.codeloop.thehub.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "thehub.job")
data class JobProperties (
    val abcdef: String
)