package com.softwareberg.base

import com.softwareberg.base.web.httpClientModule
import com.softwareberg.base.web.httpServerModule
import com.softwareberg.companies.hubModule
import com.softwareberg.scheduler.schedulerModule
import org.kodein.di.Kodein

val context = Kodein {
    import(configModule)
    import(jsonXmlModule)
    import(databaseModule)
    import(httpServerModule)
    import(httpClientModule)
    import(hubModule)
    import(schedulerModule)
}
