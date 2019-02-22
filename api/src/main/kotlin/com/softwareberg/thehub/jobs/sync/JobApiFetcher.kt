package com.softwareberg.thehub.jobs.sync

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class JobApiFetcher(private val http: RestTemplate) {

    private val log = LoggerFactory.getLogger(JobApiFetcher::class.java)

    fun fetch(host: String): List<JobsWrapper> = runBlocking {
        fetchAsync(host).await()
    }

    private fun fetchAsync(host: String): Deferred<List<JobsWrapper>> = GlobalScope.async {
        val firstJobsPage = fetchOnePageAsync(host, 1).await()
        val lastPage = firstJobsPage.jobs.pages
        fetchPageRangeAsync(host, lastPage).await()
    }

    private fun fetchOnePageAsync(host: String, page: Int): Deferred<JobsWrapper> = GlobalScope.async {
        val url = "https://$host/api/jobs?page=$page"
        log.info("fetching $url...")
        val response = http.getForEntity(url, JobsWrapper::class.java).body
        response ?: throw IllegalStateException("req: $url")
    }

    private fun fetchPageRangeAsync(host: String, lastPage: Int): Deferred<List<JobsWrapper>> = GlobalScope.async {
        (1..lastPage).map { fetchOnePageAsync(host, it) }.awaitAll()
    }

    data class JobsWrapper(val jobs: Jobs)
    data class JobPositionTypes(val name: String)
    data class Jobs(val pages: Int, val docs: List<Job>)
    data class Job(
        val key: String,
        val jobPositionTypes: List<JobPositionTypes>?,
        val locationLabel: String,
        val title: String,
        val company: Company
    )
}
