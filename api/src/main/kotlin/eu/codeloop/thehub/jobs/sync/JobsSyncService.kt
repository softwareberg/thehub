package eu.codeloop.thehub.jobs.sync

import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class JobsSyncService(private val jobFetcher: JobFetcher, private val jobHubToDatabaseService: JobHubToDatabaseService) {

    @Value("\${thehub.sync.domains}")
    private lateinit var domains: Array<String>

    fun synchronizeJobs() {
        domains.forEach(this::sync)
    }

    @Async
    fun synchronizeJobsAsync() {
        domains.forEach(this::sync)
    }

    private fun sync(host: String) {
        val jobs = jobFetcher.fetch(host)
        jobs.forEach(jobHubToDatabaseService::upsertJob)
    }
}
