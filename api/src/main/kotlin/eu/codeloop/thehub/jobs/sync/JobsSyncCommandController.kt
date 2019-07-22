package eu.codeloop.thehub.jobs.sync

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class JobsSyncCommandController(private val service: JobsSyncService) {

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/api/jobs/sync")
    fun startSync() {
        service.synchronizeJobsAsync()
    }
}
