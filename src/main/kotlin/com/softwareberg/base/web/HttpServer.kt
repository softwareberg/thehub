package com.softwareberg.base.web

import spark.Spark
import spark.Spark.port

class HttpServer(
    private val serverConfiguration: ServerConfiguration,
    private val controllers: List<Controller>
) {

    fun start() {
        port(serverConfiguration.port)
        Spark.staticFiles.location("/public")
        controllers.forEach { it.start() }
    }
}
