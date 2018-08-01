package com.softwareberg.web

import com.softwareberg.configuration.ServerConfiguration
import com.softwareberg.hub.HubController
import com.softwareberg.web.errors.ErrorsController
import spark.Request
import spark.Response
import spark.Spark.get
import spark.Spark.port

class HttpServer(
    private val serverConfiguration: ServerConfiguration,
    private val errorsController: ErrorsController,
    private val staticFilesController: StaticFilesController,
    private val hubController: HubController
) {

    fun start() {
        port(serverConfiguration.port)
        start(
            staticFilesController,
            errorsController,
            hubController
        )

        get("/health", this::health)
    }

    private fun start(vararg controllers: Controller) {
        controllers.forEach { it.start() }
    }

    private fun health(request: Request, response: Response): String {
        response.type("application/json")
        return """{"health": "ok"}"""
    }
}
