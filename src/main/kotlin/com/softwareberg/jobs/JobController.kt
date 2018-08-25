package com.softwareberg.jobs

import com.softwareberg.JsonMapper
import com.softwareberg.base.web.Controller
import com.softwareberg.base.web.PageResponse
import spark.Request
import spark.Response
import spark.Spark.get

class JobController(private val jobRepository: JobRepository, private val jsonMapper: JsonMapper) : Controller {

    override fun start() {
        get("/jobs", this::jobs, jsonMapper::write)
    }

    private fun jobs(request: Request, response: Response): PageResponse<Job> {
        response.type("application/json")
        val page = request.queryParams("page")?.toInt() ?: 1
        val pageSize = request.queryParams("pageSize")?.toInt() ?: 10
        val keyword = request.queryParams("keyword")
        val jobs = if (keyword == null) {
            jobRepository.findAll(page, pageSize)
        } else {
            jobRepository.searchByKeyword(keyword, page, pageSize)
        }
        return PageResponse(jobs, page, pageSize)
    }
}
