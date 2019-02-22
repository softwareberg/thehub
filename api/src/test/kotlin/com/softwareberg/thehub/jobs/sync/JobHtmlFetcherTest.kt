package com.softwareberg.thehub.jobs.sync

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.softwareberg.thehub.base.Files
import org.assertj.core.api.Assertions.assertThat
import org.jsoup.Jsoup
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class JobHtmlFetcherTest {

    @Test
    fun `it should parse html`() {
        // given
        val jsoupFetcher = mock<JsoupFetcher> {
            on { get(anyString()) } doReturn Jsoup.parse(Files.getResourceAsText("/htmls/head-of-international-sales.html"))
        }
        val jobHtmlFetcher = JobHtmlFetcher(jsoupFetcher)

        // when
        val details = jobHtmlFetcher.fetchDetails("https://hub.no/jobs/", "head-of-international-sales")

        // then
        assertThat(details.description).contains("build a successful sales machine")
        assertThat(details.monthlySalary).isEqualToIgnoringCase("competitive")
        assertThat(details.perks.map { it.key }).contains("lunch", "time", "coffee")
        assertThat(details.keywords).containsExactlyInAnyOrder("sales", "saas", "inside")
    }
}
