package com.michalkowol

import com.michalkowol.configurations.Configuration
import com.michalkowol.web.HttpServer
import org.flywaydb.core.Flyway
import org.h2.tools.Server

fun main(args: Array<String>) {
    Boot.start()
}

class HubSpec {

    @Test
    fun `it should give me url for all startups`() {
        val http = SimpleHttpClient.create()
        val json = JsonMapper.create()
        fun startupPage(page: Int): List<Company> {
            val url = "https://hub.no/api/startups?other=recruiting&page=$page"
            val request = HttpRequest(HttpMethod.GET, "https://hub.no/api/startups?other=recruiting&page=$page")
            val response = http.execute(request).join().body ?: throw IllegalStateException("req: $request")
            return json.read<CompaniesWrapper>(response).companies.docs
        }

        fun companies() = (1..11).toList().flatMap { startupPage(it) }
        val companiesWithPositions = companies().map {
            val doc = Jsoup.connect("https://hub.no/jobs/company/${it.key}").get()
            val positions = doc.select(".job-title").map { it.text() }
            CompanyWithPositions(it.key, it.name, positions)
        }
        companiesWithPositions.forEach {
            println("${it.name}\t${it.key}\thttps://hub.no/jobs/company/${it.key}\t${it.positions.joinToString("\t")}")
        }
    }

    data class CompaniesWrapper(val companies: Companies)
    data class Companies(val docs: List<Company>)
    data class Company(val key: String, val name: String)
    data class CompanyWithPositions(val key: String, val name: String, val positions: List<String>)

}

object Boot {

    fun start() {
        
    }
}
