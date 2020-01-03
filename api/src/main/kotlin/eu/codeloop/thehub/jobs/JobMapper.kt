package eu.codeloop.thehub.jobs

import eu.codeloop.thehub.jobs.model.JobEntity
import org.springframework.stereotype.Component

@Component
class JobMapper {
    fun map(job: JobEntity): JobDto {
        val href = "https://thehub.io/jobs/${job.jobId}"
        val logo = "https://thehub.io/${job.company.logo}"
        return JobDto(
            jobId = job.jobId,
            title = job.title,
            description = job.description,
            hasStar = job.hasStar,
            href = href,
            approvedAt = job.approvedAt,
            logo = logo
        )
    }
}
