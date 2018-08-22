package com.softwareberg.jobs

import com.softwareberg.JsonMapper
import com.softwareberg.SimpleHttpClient
import org.junit.jupiter.api.Test

class JobFetcherTest {

    @Test
    fun `it should fetch all jobs`() {
        // given
        val jsonMapper = JsonMapper.create()
        val httpClient = SimpleHttpClient.create()
        val jobFetcher = JobFetcher(httpClient, jsonMapper)

        // when
        val jobs = jobFetcher.fetchJobs()

        // then
        println(jobs)
    }
}
