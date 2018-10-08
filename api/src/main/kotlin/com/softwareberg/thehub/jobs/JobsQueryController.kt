package com.softwareberg.thehub.jobs

import com.querydsl.core.types.Predicate
import com.softwareberg.thehub.base.PageResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.querydsl.binding.QuerydslPredicate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JobsQueryController(
    private val jobsRepository: JobsRepository,
    private val jobMapper: JobMapper
) {

    @GetMapping("/api/jobs")
    fun jobs(
        @QuerydslPredicate(root = JobEntity::class) predicate: Predicate,
        pageable: Pageable
    ): PageResponse<JobDto> {
        val jobs = jobsRepository.findAll(predicate, pageable)
        val jobsDto = jobs.map(jobMapper::map)
        return PageResponse.of(jobsDto)
    }
}
