package com.softwareberg.base

import com.google.inject.Guice
import com.google.inject.Injector
import com.softwareberg.base.modules.ConfigModule
import com.softwareberg.base.modules.DatabaseModule
import com.softwareberg.base.modules.JsonXmlModule
import com.softwareberg.scheduler.SchedulerModule
import com.softwareberg.companies.HubModule
import com.softwareberg.base.web.ErrorsControllerModule
import com.softwareberg.base.web.HttpClientModule
import com.softwareberg.base.web.HttpServerModule
import com.softwareberg.base.web.StaticFilesModule

object ApplicationContext {

    val injector = createInjector()

    private fun createInjector(): Injector {
        return Guice.createInjector(
            ConfigModule(),
            ErrorsControllerModule(),
            StaticFilesModule(),
            HttpClientModule(),
            HttpServerModule(),
            JsonXmlModule(),
            DatabaseModule(),
            HubModule(),
            SchedulerModule()
        )
    }
}
