package com.softwareberg.jobs

import org.junit.jupiter.api.Test

class JobDetailFetcherTest {

    @Test
    fun `it should fetch job details`() {
        // given
        val key = "operations-project-assistant"
        val jobDetailFetcher = JobDetailFetcher()

        // when
        val jobs = jobDetailFetcher.fetchDetails(key)

        // then
        println(jobs)
    }
}
