package com.softwareberg.jobs

import org.jsoup.Jsoup
import org.jsoup.Jsoup.clean
import org.jsoup.nodes.Document
import org.jsoup.nodes.Document.OutputSettings
import org.jsoup.safety.Whitelist
import org.jsoup.select.Elements
import org.slf4j.LoggerFactory

data class JobDetail(
    val monthlySalary: String,
    val equity: String,
    val keywords: List<String>,
    val description: String,
    val perks: List<String>
)

class HtmlFetcher {
    fun get(url: String): Document = Jsoup.connect(url).timeout(10000).get()
}

class JobDetailFetcher(private val htmlFetcher: HtmlFetcher) {

    private val log = LoggerFactory.getLogger(JobDetailFetcher::class.java)

    fun fetchDetails(key: String): JobDetail {
        val url = "https://thehub.dk/jobs/$key"
        log.info("fetching $url...")
        val document = htmlFetcher.get(url)
        val compensation = document.select(".compensation h3").eachText()
        val monthlySalary = compensation[0]
        val equity = compensation[1]
        val keyword = document.select(".keywords mark").eachText()
        val description = document.select(".text-body").wholeText()
        val perks = document.select(".perk").eachText()
        return JobDetail(
            monthlySalary,
            equity,
            keyword,
            description,
            perks
        )
    }

    private fun Elements.wholeText(): String {
        return clean(this.html(), "", Whitelist.none(), OutputSettings().prettyPrint(false))
    }
}
