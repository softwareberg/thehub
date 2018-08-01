package com.softwareberg

import com.softwareberg.configuration.Configuration
import com.softwareberg.cron.FetchPositionsCronJobDefinition
import com.softwareberg.web.HttpServer
import org.flywaydb.core.Flyway

fun main(args: Array<String>) {
    Boot.start()
}

object Boot {

    fun start() {
        val injector = Configuration.injector

        val flyway = injector.getInstance<Flyway>()
        val fetchPositionsCronJob = injector.getInstance<FetchPositionsCronJobDefinition>()
        val httpServer = injector.getInstance<HttpServer>()

        flyway.clean()
        flyway.migrate()
        fetchPositionsCronJob.start()
        httpServer.start()
    }
}
