package eu.codeloop.thehub.jobs

import eu.codeloop.thehub.base.PageResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort.Direction.ASC
import org.springframework.data.domain.Sort.Direction.DESC
import org.springframework.data.web.SortDefault
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
        @SortDefault.SortDefaults(
            SortDefault(sort = ["approvedAt"], direction = DESC),
            SortDefault(sort = ["title"], direction = ASC)
        ) pageable: Pageable
    ): PageResponse<JobDto> {
        val jobs = jobQueryService.findAll(title, keyword, q, hasStar, pageable)
        val jobsDto = jobs.map(jobMapper::map)
        return PageResponse.of(jobsDto)
    }
}
