package com.softwareberg.companies

import com.softwareberg.JsonMapper
import com.softwareberg.base.web.Controller
import com.softwareberg.base.web.ListResponse
import spark.Request
import spark.Response

import spark.Spark.get

class HubController(private val jsonMapper: JsonMapper, private val companiesFetcher: CompaniesFetcher) : Controller {

    override fun start() {
        get("/companies", this::companies, jsonMapper::write)
    }

    private fun companies(request: Request, response: Response): ListResponse<CompanyWithPositions> {
        response.type("application/json")
        val companiesWithPositions = companiesFetcher.fetchCompaniesWithPositions()
        return ListResponse(companiesWithPositions)
    }
}
