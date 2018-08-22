package com.softwareberg.jobs

data class JobsWrapper(val jobs: Jobs)
data class Jobs(val pages: Int, val docs: List<Job>)
data class Job(val key: String, val positionType: String, val locationLabel: String, val title: String, val company: Company)
data class Company(val key: String, val name: String, val logo: Logo)
data class Logo(val filename: String)
