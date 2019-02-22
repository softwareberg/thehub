package com.softwareberg.thehub.jobs.sync

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.softwareberg.thehub.base.Files
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldContainAll
import org.amshove.kluent.shouldContainSome
import org.amshove.kluent.shouldEqual
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
        details.description shouldContain "build a successful sales machine"
        details.monthlySalary shouldEqual "Competitive"
        details.perks.map { it.key } shouldContainSome listOf("lunch", "time", "coffee")
        details.keywords shouldContainAll listOf("sales", "saas", "inside")
    }
}
