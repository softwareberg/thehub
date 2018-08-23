package com.softwareberg.jobs

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
    val perks: List<Perk>
)

data class Company(val key: String, val name: String, val logo: Logo)
data class Logo(val filename: String)
data class Perk(val key: String, val description: String)
