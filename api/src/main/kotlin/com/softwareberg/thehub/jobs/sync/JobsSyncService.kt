package com.softwareberg.thehub.jobs.sync

import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class JobsSyncService(private val jobFetcher: JobFetcher, private val jobHubToDatabaseService: JobHubToDatabaseService) {

    @Value("\${thehub.sync.domains}")
    private lateinit var domains: Array<String>

    @Scheduled(fixedRateString = "\${thehub.sync.scheduling.fixedRate-in-milliseconds}")
    fun sync() {
        domains.forEach(this::sync)
    }

    private fun sync(host: String) {
        val jobs = jobFetcher.fetch(host)
        jobs.forEach(jobHubToDatabaseService::upsertJob)
    }
}
