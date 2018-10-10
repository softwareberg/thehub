package com.softwareberg.thehub.base

import org.jooq.DSLContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DatabaseSetupConfiguration {

    @Bean
    fun databaseSetup(db: DSLContext): DatabaseSetup {
        return DatabaseSetup(db)
    }
}
