package com.softwareberg.hubno

import com.softwareberg.HttpClient
import com.softwareberg.HttpMethod.GET
import com.softwareberg.HttpRequest
import com.softwareberg.JsonMapper
import com.softwareberg.SimpleHttpClient
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory

data class CompaniesWrapper(val companies: Companies)
data class Companies(val docs: List<Company>)
data class Company(val key: String, val name: String)
data class CompanyWithPositions(val key: String, val name: String, val positions: List<String>)

class HubNoFetcher(
    private val http: HttpClient = SimpleHttpClient.create(),
    private val json: JsonMapper = JsonMapper.create()
) {

    private val log = LoggerFactory.getLogger(HubNoFetcher::class.java)

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
