package eu.codeloop.thehub.jobs

import eu.codeloop.thehub.base.PageResponse
import eu.codeloop.thehub.errors.GenericError
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
        throw GenericError(123, "dsfgdfgdsfgdsgf")
        return PageResponse.of(jobsDto)
    }
}
