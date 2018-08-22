package com.softwareberg

import com.softwareberg.base.ApplicationContext
import com.softwareberg.base.getInstance
import com.softwareberg.scheduler.FetchPositionsJobDefinition
import com.softwareberg.base.web.HttpServer
import org.flywaydb.core.Flyway

fun main(args: Array<String>) {
    Boot.start()
}

object Boot {

    fun start() {
        val injector = ApplicationContext.injector

        val flyway = injector.getInstance<Flyway>()
        val fetchPositionsJob = injector.getInstance<FetchPositionsJobDefinition>()
        val httpServer = injector.getInstance<HttpServer>()

        flyway.clean()
        flyway.migrate()
        fetchPositionsJob.start()
        httpServer.start()
    }
}
