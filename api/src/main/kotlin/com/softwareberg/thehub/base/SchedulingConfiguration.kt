package com.softwareberg.thehub.base

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@ConditionalOnProperty(
    prefix = "thehub.scheduling", name = ["enable"], havingValue = "true", matchIfMissing = true
)
@Configuration
@EnableScheduling
class SchedulingConfiguration
