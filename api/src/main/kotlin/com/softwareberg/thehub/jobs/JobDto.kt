package com.softwareberg.thehub.jobs

data class JobDto(val jobId: String, val title: String, val company: CompanyDto? = null, val keywords: List<String> = emptyList())
