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
        @RequestParam title: String?,
        @RequestParam keyword: String?,
        @RequestParam q: String?,
        @RequestParam hasStar: Boolean?,
        pageable: Pageable
    ): PageResponse<JobDto> {
        val jobs = jobQueryService.findAll(title, keyword, q, hasStar, pageable)
        val jobsDto = jobs.map(jobMapper::map)
        return PageResponse.of(jobsDto)
    }
}
