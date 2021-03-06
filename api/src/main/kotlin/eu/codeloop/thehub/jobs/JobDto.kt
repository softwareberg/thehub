package eu.codeloop.thehub.jobs

import java.time.OffsetDateTime

data class JobDto(
    val jobId: String,
    val title: String,
    val description: String,
    val hasStar: Boolean,
    val keywords: List<String>,
    val href: String,
    val approvedAt: OffsetDateTime,
    val logo: String,
    val poster: String
)
