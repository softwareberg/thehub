package com.softwareberg.jobs.sync

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.softwareberg.readText
import org.assertj.core.api.Assertions.assertThat
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test

class JobHtmlFetcherTest {

    private val htmlFetcher = mock<JsoupFetcher>()
    private val jobDetailFetcher = JobHtmlFetcher(htmlFetcher)

    @Test
    fun `it should fetch job details`() {
        // given
        val host = "hub.no"
        whenever(htmlFetcher.get(any())).doReturn(Jsoup.parse(readText("/http/jobs/job.html")))
        val key = "social-media-specialist-wanted"

        // when
        val job = jobDetailFetcher.fetchDetails(host, key)

        // then
        assertThat(job.monthlySalary).isEqualTo("Competitive")
        assertThat(job.equity).isEqualTo("To be negotiated")
        assertThat(job.keywords).hasSize(8).contains("Social Media")
        assertThat(job.description).contains("online strategy")
        assertThat(job.perks).hasSize(8)
        assertThat(job.perks.first().key).isEqualTo("coffee")
        assertThat(job.perks.first().description).isEqualTo("Free coffee / tea: Get your caffeine fix for free")
    }
}
