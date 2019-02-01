package com.softwareberg.thehub.jobs.model

import java.util.ArrayList
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "job_perks")
class JobPerkEntity {

    @Id
    @Column(name = "job_perk_id", nullable = false)
    lateinit var jobPerkId: String

    @Column(name = "description", nullable = false)
    lateinit var description: String

    @ManyToMany(mappedBy = "perks")
    val jobs: MutableList<JobEntity> = ArrayList()
}
