package com.softwareberg.scheduler

import com.softwareberg.hub.HubFetcher
import org.quartz.Job
import org.quartz.JobExecutionContext

class FetchPositionsJob : Job {

    override fun execute(context: JobExecutionContext) {
        val jobDataMap = context.jobDetail.jobDataMap
        val hubFetcher = jobDataMap["hubFetcher"] as HubFetcher
        hubFetcher.fetchCompaniesWithPositions()
    }
}
