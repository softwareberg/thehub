package com.softwareberg.web

import spark.Spark

class StaticFilesController : Controller {

    override fun start() {
        Spark.staticFiles.location("/public")
    }
}
