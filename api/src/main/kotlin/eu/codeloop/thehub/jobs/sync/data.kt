package eu.codeloop.thehub.jobs.sync

import java.time.OffsetDateTime

data class Job(
    val key: String,
    val positionType: String,
    val locationLabel: String,
    val title: String,
    val company: Company,
    val monthlySalary: String?,
    val equity: String?,
    val keywords: List<String>,
    val description: String,
    val perks: List<Perk>,
    val host: String,
    val approvedAt: OffsetDateTime
)

data class Company(val key: String, val name: String, val logo: Logo)
data class Logo(val filename: String)
data class Perk(val key: String, val description: String)
