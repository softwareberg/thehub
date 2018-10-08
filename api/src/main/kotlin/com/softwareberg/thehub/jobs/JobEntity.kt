package com.softwareberg.thehub.jobs

import java.util.ArrayList
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "jobs")
class JobEntity {

    @Id
    @Column(name = "job_id", nullable = false)
    lateinit var jobId: String

    @Column(name = "title", nullable = false)
    lateinit var title: String

    @Column(name = "monthly_salary", nullable = false)
    lateinit var monthlySalary: String

    @Column(name = "equity", nullable = false)
    lateinit var equity: String

    @Column(name = "description", nullable = false)
    lateinit var description: String

    @Column(name = "position_type", nullable = false)
    lateinit var positionType: String

    @Column(name = "has_star", nullable = false)
    var hasStar: Boolean = false

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false

    @ManyToOne
    @JoinColumn(
        name = "company_id",
        referencedColumnName = "company_id",
        insertable = true,
        updatable = true
    )
    lateinit var company: CompanyEntity

    @ManyToMany(mappedBy = "jobs")
    var keywords: MutableList<JobKeywordEntity> = ArrayList()
}
