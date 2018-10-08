package com.softwareberg.thehub.jobs

import java.util.ArrayList
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "job_keywords")
class JobKeywordEntity {

    @Id
    @Column(name = "keyword", nullable = false)
    lateinit var keyword: String

    @ManyToMany(cascade = [(CascadeType.MERGE)])
    @JoinTable(
        name = "jobs_job_keywords",
        joinColumns = [JoinColumn(name = "keyword", referencedColumnName = "keyword")],
        inverseJoinColumns = [JoinColumn(name = "job_id", referencedColumnName = "job_id")]
    )
    var jobs: MutableList<JobEntity> = ArrayList()
}
