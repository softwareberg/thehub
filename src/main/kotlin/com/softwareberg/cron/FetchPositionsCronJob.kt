package com.softwareberg.cron

import com.softwareberg.hub.HubFetcher
import org.quartz.Job
import org.quartz.JobExecutionContext

class FetchPositionsCronJob : Job {

    override fun execute(context: JobExecutionContext) {
        val jobDataMap = context.jobDetail.jobDataMap
        val hubFetcher = jobDataMap["hubFetcher"] as HubFetcher
        hubFetcher.fetchCompaniesWithPositions(1)
    }
}
