package com.softwareberg.hub

import com.softwareberg.HttpClient
import com.softwareberg.HttpMethod.GET
import com.softwareberg.HttpRequest
import com.softwareberg.JsonMapper
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory

class HubFetcher(
    private val http: HttpClient,
    private val json: JsonMapper
) {

    private val log = LoggerFactory.getLogger(HubFetcher::class.java)

    fun fetchCompaniesWithPositions(lastPage: Int): List<CompanyWithPositions> {
        val companies = fetchAllCompanies(lastPage)
        return companies.map(this::fetchPositions)
    }

    private fun startupPage(page: Int): List<Company> {
        val url = "https://thehub.dk/api/startups?other=recruiting&page=$page"
        val request = HttpRequest(GET, url)
        val response = http.execute(request).join().body ?: throw IllegalStateException("req: $request")
        return json.read<CompaniesWrapper>(response).companies.docs
    }

    private fun fetchPositions(company: Company): CompanyWithPositions {
        log.info("fetching for ${company.name}...")
        val doc = Jsoup.connect("https://thehub.dk/jobs/company/${company.key}").timeout(3000).get()
        val positions = doc.select(".job-title").map { it.text() }
        return CompanyWithPositions(company.key, company.name, positions)
    }

    private fun fetchAllCompanies(lastPage: Int): List<Company> = (1..lastPage).flatMap { startupPage(it) }
}
