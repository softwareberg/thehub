package com.softwareberg.jobs

import com.softwareberg.Database
import com.softwareberg.Extractor
import com.softwareberg.params

class JobRepository(private val database: Database) {

    private data class JobRow(val id: String, val title: String, val description: String, val keyword: String, val domain: String)

    private val jobExtractor: Extractor<JobRow> = { r -> JobRow(r.string("job_id"), r.string("title"), r.string("description"), r.string("keyword"), r.string("domain")) }

    fun findAll(page: Int, pageSize: Int): List<Job> {
        val sql = """
        SELECT j.job_id, j.title, j.description, c.domain, k.keyword
        FROM (SELECT * FROM jobs LIMIT :limit OFFSET :offset) AS j
          INNER JOIN companies AS c ON j.company_id = c.company_id
          INNER JOIN jobs_job_keywords AS k ON j.job_id = k.job_id
        """
        val offset = offset(page, pageSize)
        val jobsRows = database.findAll(sql.params("limit" to pageSize, "offset" to offset), jobExtractor)
        return mapToJob(jobsRows)
    }

    fun searchByKeyword(keyword: String, page: Int, pageSize: Int): List<Job> {
        val sql = """
        SELECT j.job_id, j.title, j.description, c.domain, k.keyword
        FROM jobs AS j
          INNER JOIN companies AS c ON j.company_id = c.company_id
          INNER JOIN jobs_job_keywords AS k ON j.job_id = k.job_id
        WHERE k.keyword ILIKE :keyword
        LIMIT :limit OFFSET :offset
        """
        val offset = offset(page, pageSize)
        val jobsRows = database.findAll(sql.params("keyword" to keyword, "limit" to pageSize, "offset" to offset), jobExtractor)
        return mapToJob(jobsRows)
    }

    private fun offset(page: Int, pageSize: Int) = if (page < 1) 0 else (page - 1) * pageSize

    private fun mapToJob(jobsRows: List<JobRow>): List<Job> {
        val jobById = mutableMapOf<String, Job>()
        val keywordsByJobId = mutableMapOf<String, MutableList<String>>()
        jobsRows.forEach { jobRow ->
            val keywords = keywordsByJobId.getOrDefault(jobRow.id, mutableListOf())
            keywords.add(jobRow.keyword)
            val href = "https://${jobRow.domain}/jobs/${jobRow.id}"
            val job = jobById[jobRow.id] ?: Job(jobRow.title, jobRow.description, keywords, href)
            jobById[jobRow.id] = job
        }
        return jobById.values.toList()
    }
}
