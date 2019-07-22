package eu.codeloop.thehub.jobs

import eu.codeloop.thehub.jobs.model.JobEntity
import org.springframework.stereotype.Component

@Component
class JobMapper {
    fun map(job: JobEntity): JobDto {
        val href = "https://${job.company.domain.domain}/jobs/${job.jobId}"
        val keywords = job.keywords.map { it.keyword }
        return JobDto(
            jobId = job.jobId,
            title = job.title,
            description = job.description,
            hasStar = job.hasStar,
            href = href,
            keywords = keywords
        )
    }
}
