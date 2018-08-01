package com.softwareberg.hub

import com.softwareberg.JsonMapper
import com.softwareberg.web.Controller
import com.softwareberg.web.ListResponse
import spark.Request
import spark.Response

import spark.Spark.get

class HubController(private val jsonMapper: JsonMapper, private val hubFetcher: HubFetcher) : Controller {

    override fun start() {
        get("/hubs/no", this::hubno, jsonMapper::write)
    }

    private fun hubno(request: Request, response: Response): ListResponse<CompanyWithPositions> {
        response.type("application/json")
        val companiesWithPositions = hubFetcher.fetchCompaniesWithPositions(1)
        return ListResponse(companiesWithPositions)
    }
}
