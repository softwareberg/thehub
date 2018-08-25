package com.softwareberg.base

import com.softwareberg.base.web.httpClientModule
import com.softwareberg.base.web.httpServerModule
import com.softwareberg.jobs.jobModule
import com.softwareberg.jobs.sync.jobSyncModule
import org.kodein.di.Kodein

val context = Kodein {
    import(configModule)
    import(jsonXmlModule)
    import(databaseModule)
    import(httpServerModule)
    import(httpClientModule)
    import(cronModule)
    import(jobSyncModule)
    import(jobModule)
}
