package com.softwareberg.thehub.jobs

import com.softwareberg.thehub.base.PageResponse
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class JobQueryController(private val jobQueryService: JobQueryService, private val jobMapper: JobMapper) {

    @GetMapping("/api/jobs")
    fun jobs(
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) keyword: String?,
        @RequestParam(required = false) q: String?,
        pageable: Pageable
    ): PageResponse<JobDto> {
        val jobs = jobQueryService.findAll(title, keyword, q, pageable)
        val jobsDto = jobs.map(jobMapper::map)
        return PageResponse.of(jobsDto)
    }
}
