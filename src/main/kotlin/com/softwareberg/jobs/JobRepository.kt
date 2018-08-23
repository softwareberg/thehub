package com.softwareberg.jobs

import com.softwareberg.Database
import com.softwareberg.params

class JobRepository(private val database: Database) {

    fun updateOrCreate(job: Job) {

    }

    fun updateOrCreate(company: Company) {
        database.insert("SELECT *".params())
    }
}
