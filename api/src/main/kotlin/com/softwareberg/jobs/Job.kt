package com.softwareberg.jobs

data class Job(val jobId: String, val title: String, val description: String, val hasStar: Boolean, val keywords: List<String>, val href: String)
