package com.softwareberg.thehub.jobs

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class JobsController {

    @GetMapping("/api/jobs")
    fun jobs(): Mono<Job> {
        return Mono.just(Job("", "", "", false, emptyList(), ""))
    }
}
