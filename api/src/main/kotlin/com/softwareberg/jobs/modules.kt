package com.softwareberg.jobs

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val jobModule = Kodein.Module("jobModule") {
    bind<JobRepository>() with singleton { JobRepository(instance()) }
    bind<JobController>() with singleton { JobController(instance(), instance()) }
}
