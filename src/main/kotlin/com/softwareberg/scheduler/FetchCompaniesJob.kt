package com.softwareberg.scheduler

import com.softwareberg.companies.CompaniesFetcher
import org.quartz.Job
import org.quartz.JobExecutionContext

class FetchCompaniesJob : Job {

    override fun execute(context: JobExecutionContext) {
        val jobDataMap = context.jobDetail.jobDataMap
        val companiesFetcher = jobDataMap["companiesFetcher"] as CompaniesFetcher
        companiesFetcher.fetchCompaniesWithPositions()
    }
}
