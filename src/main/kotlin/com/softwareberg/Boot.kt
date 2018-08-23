package com.softwareberg

import com.softwareberg.base.context
import com.softwareberg.base.web.HttpServer
import com.softwareberg.scheduler.FetchPositionsJobDefinition
import org.flywaydb.core.Flyway
import org.kodein.di.generic.instance

fun main(args: Array<String>) {
    Boot.start()
}

object Boot {

    fun start() {
        val flyway: Flyway by context.instance()
        val fetchPositionsJob: FetchPositionsJobDefinition by context.instance()
        val httpServer: HttpServer by context.instance()

        flyway.clean()
        flyway.migrate()
        fetchPositionsJob.start()
        httpServer.start()
    }
}
