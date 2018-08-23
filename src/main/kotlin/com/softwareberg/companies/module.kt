package com.softwareberg.companies

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val hubModule = Kodein.Module("hubModule") {
    bind<CompaniesFetcher>() with singleton { CompaniesFetcher(instance(), instance()) }
    bind<HubController>() with singleton { HubController(instance(), instance()) }
}
