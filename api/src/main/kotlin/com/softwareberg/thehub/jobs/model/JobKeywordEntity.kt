package com.softwareberg.thehub.jobs.model

import java.util.ArrayList
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "job_keywords")
class JobKeywordEntity {

    @Id
    @Column(name = "keyword", nullable = false)
    lateinit var keyword: String

    @ManyToMany(mappedBy = "keywords")
    val jobs: MutableList<JobEntity> = ArrayList()
}
