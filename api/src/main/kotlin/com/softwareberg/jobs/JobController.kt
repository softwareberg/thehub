package com.softwareberg.jobs

import com.fasterxml.jackson.databind.JsonNode
import com.softwareberg.JsonMapper
import com.softwareberg.base.web.Controller
import com.softwareberg.base.web.PageResponse
import com.softwareberg.base.web.errors.BadRequestException
import com.softwareberg.base.web.errors.NotFoundException
import spark.Request
import spark.Response
import spark.Spark.delete
import spark.Spark.get
import spark.Spark.patch
import java.net.HttpURLConnection.HTTP_NO_CONTENT

class JobController(private val jobRepository: JobRepository, private val jsonMapper: JsonMapper) : Controller {

    override fun start() {
        get("/api/jobs", this::jobs, jsonMapper::write)
        patch("/api/jobs/:jobId", this::modifyJob)
        delete("/api/jobs/:jobId", this::deleteJob)
    }

    private fun jobs(request: Request, response: Response): PageResponse<Job> {
        response.type("application/json")
        val page = request.queryParams("page")?.toIntOrNull() ?: 1
        val pageSize = request.queryParams("pageSize")?.toIntOrNull() ?: 10
        val keyword: String? = request.queryParams("keyword")
        val hasStar = request.queryParams("hasStar")?.toBoolean()
        val jobs = if (keyword == null) {
            if (hasStar != null && hasStar) {
                jobRepository.findWithStars()
            } else {
                jobRepository.findAll(page, pageSize)
            }
        } else {
            jobRepository.searchByKeyword(keyword)
        }
        return PageResponse(jobs, page, pageSize)
    }

    private fun deleteJob(request: Request, response: Response): String {
        val jobId = request.params("jobId") ?: throw BadRequestException("Job Id is missing")
        jobRepository.delete(jobId)
        response.status(HTTP_NO_CONTENT)
        return ""
    }

    private fun modifyJob(request: Request, response: Response): String {
        val jobId = request.params("jobId") ?: throw BadRequestException("Job Id is missing")
        val jobRequest = parseBodyToJsonNode(request)
        if (!jobRequest.path("jobId").isMissingNode) {
            throw BadRequestException("Job Id should")
        }
        if (jobRepository.notExists(jobId)) {
            throw NotFoundException("Job with id=$jobId not found")
        }
        val hasStarJson = jobRequest.path("hasStar")
        if (!hasStarJson.isMissingNode && hasStarJson.isBoolean) {
            val hasStar = hasStarJson.asBoolean()
            jobRepository.setStar(jobId, hasStar)
        }
        response.status(HTTP_NO_CONTENT)
        return ""
    }

    private fun parseBodyToJsonNode(request: Request): JsonNode {
        try {
            val body = request.body()
            return jsonMapper.read(body)
        } catch (e: Exception) {
            throw BadRequestException("Invalid body", e)
        }
    }
}
