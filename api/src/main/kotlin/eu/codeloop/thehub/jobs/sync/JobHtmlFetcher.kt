package eu.codeloop.thehub.jobs.sync

import org.apache.commons.text.StringEscapeUtils.unescapeHtml4
import org.jsoup.Jsoup
import org.jsoup.Jsoup.clean
import org.jsoup.nodes.Document
import org.jsoup.nodes.Document.OutputSettings
import org.jsoup.safety.Whitelist
import org.jsoup.select.Elements
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

@Service
class JobHtmlFetcher(private val jsoupFetcher: JsoupFetcher) {

    private val log = LoggerFactory.getLogger(JobHtmlFetcher::class.java)

    fun fetchDetails(host: String, key: String): Job {
        val url = "https://$host/jobs/$key"
        log.info("fetching $url...")
        val document = jsoupFetcher.get(url)
        val compensation = document.select(".compensation h3").eachText()
        val monthlySalary = compensation.getOrNull(0)
        val equity = compensation.getOrNull(1)
        val keyword = document.select(".keywords mark").eachText()
        val description = unescapeHtml4(document.select(".text-body").wholeText())
        val perks = extractPerks(document)
        val poster = extractPoster(document)
        return Job(monthlySalary, equity, keyword, description, perks, poster)
    }

    private val patternLink = Pattern.compile("^.*background-image:url\\(([^)]*)\\).*$")
    private val patternCleanLink = Pattern.compile("^/files/(.*)/(.*)$")

    private fun extractPoster(document: Document): Poster {
        val css = document.select("hero").attr("style")

        val matcherLink = patternLink.matcher(css)
        val link = if (matcherLink.matches()) matcherLink.group(1) else ""

        val matcherCleanLink = patternCleanLink.matcher(link)
        val filename = if (matcherCleanLink.matches()) "${matcherCleanLink.group(1)}/${matcherCleanLink.group(2)}" else ""

        return Poster(filename)
    }

    private fun extractPerks(document: Document): List<Perk> {
        return document.select(".perk").mapNotNull { perk ->
            val className = perk.selectFirst(".icon").className().split(" ").lastOrNull()
            val key = className?.removePrefix("icon-")
            val description = perk.text()
            if (key == null) {
                null
            } else {
                Perk(key, description)
            }
        }
    }

    private fun Elements.wholeText(): String = clean(this.html(), "", Whitelist.none(), OutputSettings().prettyPrint(false))

    data class Job(
        val monthlySalary: String?,
        val equity: String?,
        val keywords: List<String>,
        val description: String,
        val perks: List<Perk>,
        val poster: Poster
    )
}

@Service
class JsoupFetcher {

    fun get(url: String): Document = Jsoup.connect(url).timeout(timeout).get()

    companion object {
        private val timeout = TimeUnit.SECONDS.convert(10, TimeUnit.MILLISECONDS).toInt()
    }
}
