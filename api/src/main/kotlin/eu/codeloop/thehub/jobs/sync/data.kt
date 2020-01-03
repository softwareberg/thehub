package eu.codeloop.thehub.jobs.sync

import java.time.OffsetDateTime

data class Job(
    val key: String,
    val jobRole: String,
    val locationLabel: String,
    val title: String,
    val company: Company,
    val salary: String,
    val equity: String,
    val description: String,
    val host: String,
    val createdAt: OffsetDateTime
)

data class Company(val key: String, val name: String, val logoImage: Image)

data class Image(val path: String)
