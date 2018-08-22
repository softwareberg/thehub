package com.softwareberg.jobs

import org.jsoup.Jsoup
import org.jsoup.Jsoup.clean
import org.jsoup.nodes.Document.OutputSettings
import org.jsoup.safety.Whitelist
import org.jsoup.select.Elements
import org.slf4j.LoggerFactory


data class JobDetail(
    val monthlySalary: String,
    val keywords: List<String>,
    val description: String,
    val perks: String
)

class JobDetailFetcher {

    private val log = LoggerFactory.getLogger(JobDetailFetcher::class.java)

    fun fetchDetails(key: String): JobDetail {
        val url = "https://thehub.dk/jobs/$key"
        val document = Jsoup.connect(url).timeout(10000).get()
        val keyword = document.select(".keywords mark").eachText()
        val description = document.select(".text-body").wholeText()
        return JobDetail(
            "todo",
            keyword,
            description,
            "todo"
        )
    }

    private fun Elements.wholeText(): String {
        return clean(this.html(), "", Whitelist.none(), OutputSettings().prettyPrint(false))
    }
}
