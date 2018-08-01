package com.softwareberg

import com.softwareberg.configurations.Configuration
import com.softwareberg.web.HttpServer
import org.flywaydb.core.Flyway
import org.h2.tools.Server

fun main(args: Array<String>) {
    Boot.start()
}

object Boot {

    fun start() {
        val injector = Configuration.injector

        val h2DatabaseServer = injector.getInstance<Server>()
        val flyway = injector.getInstance<Flyway>()
        val httpServer = injector.getInstance<HttpServer>()

        h2DatabaseServer.start()
        flyway.clean()
        flyway.migrate()
        httpServer.start()
    }
}
