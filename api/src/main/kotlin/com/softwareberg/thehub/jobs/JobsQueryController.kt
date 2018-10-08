package com.softwareberg.thehub.jobs

import com.softwareberg.thehub.base.PageResponse
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JobsQueryController(
    private val jobsRepository: JobsRepository,
    private val companiesRepository: CompaniesRepository
) {

    @GetMapping("/api/jobs")
    fun jobs(page: Pageable): PageResponse<JobDto> {
        val jobs = jobsRepository.findAll(page)
        val jobsDto = jobs.map {
            val company = CompanyDto(companyId = it.company.companyId, name = it.company.name)
            JobDto(jobId = it.jobId, title = it.title, company = company, keywords = it.keywords.map { it.keyword })
        }
        return PageResponse.of(jobsDto)
    }

    @GetMapping("/api/companies")
    fun companies(page: Pageable): PageResponse<CompanyDto> {
        val companies = companiesRepository.findAll(page)
        val companiesDto = companies.map {
            val jobs = it.jobs.map { JobDto(jobId = it.jobId, title = it.title, keywords = it.keywords.map { it.keyword }) }
            CompanyDto(companyId = it.companyId, name = it.name, jobs = jobs)
        }
        return PageResponse.of(companiesDto)
    }
}
