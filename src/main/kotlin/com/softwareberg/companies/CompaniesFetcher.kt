package com.softwareberg.companies

import com.softwareberg.HttpClient
import com.softwareberg.HttpMethod.GET
import com.softwareberg.HttpRequest
import com.softwareberg.JsonMapper
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory

class CompaniesFetcher(
    private val http: HttpClient,
    private val json: JsonMapper
) {

    private val log = LoggerFactory.getLogger(CompaniesFetcher::class.java)

    fun fetchCompaniesWithPositions(): List<CompanyWithPositions> {
        val lastPage = fetchCompaniesPage(1).companies.pages
        val companies = fetchAllCompanies(lastPage)
        return companies.map(this::fetchPositions)
    }

    private fun startupPage(page: Int): List<Company> {
        return fetchCompaniesPage(page).companies.docs
    }

    private fun fetchCompaniesPage(page: Int): CompaniesWrapper {
        val url = "https://thehub.dk/api/startups?other=recruiting&page=$page"
        log.info("fetching $url...")
        val request = HttpRequest(GET, url)
        val response = http.execute(request).join().body ?: throw IllegalStateException("req: $request")
        return json.read(response)
    }

    private fun fetchPositions(company: Company): CompanyWithPositions {
        val url = "https://thehub.dk/jobs/company/${company.key}"
        val doc = Jsoup.connect(url).timeout(3000).get()
        log.info("fetching for ${company.name} using $url...")
        val positions = doc.select(".job-title").map { it.text() }
        return CompanyWithPositions(company.key, company.name, positions)
    }

    private fun fetchAllCompanies(lastPage: Int): List<Company> = (1..lastPage).flatMap { startupPage(it) }
}
