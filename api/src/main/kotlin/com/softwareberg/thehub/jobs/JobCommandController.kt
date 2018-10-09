package com.softwareberg.thehub.jobs

import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class JobCommandController {

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/api/jobs/{jobId}")
    fun deleteJob(@PathVariable jobId: String) {
    }

    @ResponseStatus(NO_CONTENT)
    @PatchMapping("/api/jobs/{jobId}")
    fun modifyJob(@PathVariable jobId: String) {
    }
}
