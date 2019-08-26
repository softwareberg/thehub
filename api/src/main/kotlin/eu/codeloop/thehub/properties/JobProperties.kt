package eu.codeloop.thehub.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "thehub.job")
class JobProperties {
    lateinit var `approved-at-default`: String
}