package eu.codeloop.thehub.jobs.sync

import eu.codeloop.thehub.jobs.sync.JobApiFetcher.RawJob
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class JobFetcher(private val jobFetcher: JobApiFetcher) {

    private val log = LoggerFactory.getLogger(JobFetcher::class.java)

    fun fetch(host: String): List<Job> {
        val pages = jobFetcher.fetch(host)
        val jobsFromApi = pages.flatMap { it.docs }
        return jobsFromApi.mapNotNull { api -> fetch(host, api) }
    }

    @SuppressWarnings("TooGenericExceptionCaught")
    private fun fetch(host: String, job: RawJob): Job? {
        try {
            val createdAt = OffsetDateTime.parse(job.createdAt)
            val locationLabel = job.location.locality ?: job.location.address
            return Job(
                job.key,
                job.jobRole,
                locationLabel,
                job.title,
                job.company,
                job.salary,
                job.equity,
                job.description,
                host,
                createdAt
            )
        } catch (e: Exception) {
            log.info("Error on fetching details from ${job.key}", e)
            return null
        }
    }
}
