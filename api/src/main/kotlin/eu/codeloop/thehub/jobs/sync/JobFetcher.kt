package eu.codeloop.thehub.jobs.sync

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class JobFetcher(private val jonFetcher: JobApiFetcher, private val jobHtmlFetcher: JobHtmlFetcher) {

    private val log = LoggerFactory.getLogger(JobFetcher::class.java)

    fun fetch(host: String): List<Job> {
        val pages = jonFetcher.fetch(host)
        val jobsFromApi = pages.flatMap { it.jobs.docs }
        return jobsFromApi.mapNotNull { api -> fetch(host, api) }
    }

    @SuppressWarnings("TooGenericExceptionCaught")
    private fun fetch(host: String, api: JobApiFetcher.Job): Job? {
        try {
            val html = jobHtmlFetcher.fetchDetails(host, api.key)
            val approvedAt = OffsetDateTime.parse(api.approvedAt ?: "1970-01-01T00:00:00.000Z")
            return Job(
                api.key,
                api.jobPositionTypes?.firstOrNull()?.name ?: "unknown",
                api.locationLabel,
                api.title,
                api.company,
                html.monthlySalary,
                html.equity,
                html.keywords,
                html.description,
                html.perks,
                host,
                approvedAt
            )
        } catch (e: Exception) {
            log.info("Error on fetching details from ${api.key}", e)
            return null
        }
    }
}
