package com.softwareberg.thehub.jobs.model

import java.util.ArrayList
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "jobs")
class JobEntity {

    @Id
    @Column(name = "job_id", nullable = false)
    lateinit var jobId: String

    @Column(name = "company_id", nullable = false)
    lateinit var companyId: String

    @Column(name = "title", nullable = false)
    lateinit var title: String

    @Column(name = "description", nullable = false)
    lateinit var description: String

    @Column(name = "has_star", nullable = false)
    var hasStar: Boolean = false

    @ManyToOne
    @JoinColumn(
        name = "company_id",
        referencedColumnName = "company_id",
        insertable = false,
        updatable = false,
        nullable = false
    )
    lateinit var company: CompanyEntity

    @ManyToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(
        name = "equity",
        referencedColumnName = "equity",
        insertable = true,
        updatable = true,
        nullable = true
    )
    var equity: EquityEntity? = null

    @ManyToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(
        name = "monthly_salary",
        referencedColumnName = "monthly_salary",
        insertable = true,
        updatable = true,
        nullable = true
    )
    var monthlySalary: MonthlySalaryEntity? = null

    @ManyToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(
        name = "position_type",
        referencedColumnName = "position_type",
        insertable = true,
        updatable = true,
        nullable = false
    )
    lateinit var positionType: PositionsTypeEntity

    @ManyToMany(cascade = [CascadeType.MERGE])
    @JoinTable(
        name = "jobs_job_keywords",
        joinColumns = [JoinColumn(name = "job_id", referencedColumnName = "job_id")],
        inverseJoinColumns = [JoinColumn(name = "keyword", referencedColumnName = "keyword")]
    )
    val keywords: MutableList<JobKeywordEntity> = ArrayList()

    @ManyToMany(cascade = [CascadeType.MERGE])
    @JoinTable(
        name = "jobs_job_perks",
        joinColumns = [JoinColumn(name = "job_id", referencedColumnName = "job_id")],
        inverseJoinColumns = [JoinColumn(name = "job_perk_id", referencedColumnName = "job_perk_id")]
    )
    val perks: MutableList<JobPerkEntity> = ArrayList()
}
