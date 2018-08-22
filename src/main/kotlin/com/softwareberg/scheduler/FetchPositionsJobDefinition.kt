package com.softwareberg.scheduler

import com.softwareberg.companies.CompaniesFetcher
import org.quartz.JobBuilder.newJob
import org.quartz.JobDataMap
import org.quartz.Scheduler
import org.quartz.SimpleScheduleBuilder.simpleSchedule
import org.quartz.TriggerBuilder.newTrigger


class FetchPositionsJobDefinition(private val scheduler: Scheduler, private val companiesFetcher: CompaniesFetcher) {

    fun start() {
        val group = "fetchCompanies"

        val jobDataMap = JobDataMap()
        jobDataMap["companiesFetcher"] = companiesFetcher

        val job = newJob(FetchCompaniesJob::class.java)
            .withIdentity("no", group)
            .usingJobData(jobDataMap)
            .build()

        val trigger = newTrigger()
            .withIdentity("fetchPositionsJobTrigger", group)
            .startNow()
            .withSchedule(simpleSchedule()
                .withIntervalInMinutes(3)
                .repeatForever())
            .build()

        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, trigger)
    }
}
