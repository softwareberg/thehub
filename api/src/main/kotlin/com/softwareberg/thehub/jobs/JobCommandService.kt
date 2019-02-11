package com.softwareberg.thehub.jobs

import org.springframework.stereotype.Service

@Service
class JobCommandService(private val jobsRepository: JobRepository) {

    fun starJob(jobId: String, hasStar: Boolean) {
        val job = jobsRepository.findById(jobId).orElseThrow { IllegalStateException("Cannot find a job with id=$jobId") }
        job.hasStar = hasStar
        jobsRepository.save(job)
    }

    fun deleteJob(jobId: String) {
        val job = jobsRepository.findById(jobId).orElseThrow { IllegalStateException("Cannot find a job with id=$jobId") }
        job.isDeleted = true
        jobsRepository.save(job)
    }
}
