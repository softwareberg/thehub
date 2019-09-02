package eu.codeloop.thehub.jobs

import eu.codeloop.thehub.jobs.model.JobEntity
import org.springframework.stereotype.Component

@Component
class JobMapper {
    fun map(job: JobEntity): JobDto {
        val href = "${job.company.domain.domain}/jobs/${job.jobId}"
        val keywords = job.keywords.map { it.keyword }
        val logo = "${job.company.domain.domain}/files/${job.company.logo}"
        val poster = if (job.poster != "") "${job.company.domain.domain}/files/${job.poster}" else "${job.company.domain.domain}/images/default_cover.jpg"
        return JobDto(
            jobId = job.jobId,
            title = job.title,
            description = job.description,
            hasStar = job.hasStar,
            href = href,
            keywords = keywords,
            approvedAt = job.approvedAt,
            logo = logo,
            poster = poster
        )
    }
}
