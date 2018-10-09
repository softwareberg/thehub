package com.softwareberg.thehub.jobs.model

import java.util.ArrayList
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "companies")
class CompanyEntity {

    @Id
    @Column(name = "company_id", nullable = false)
    lateinit var companyId: String

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "logo", nullable = false)
    lateinit var logo: String

    @OneToMany
    @JoinColumn(
        name = "company_id",
        referencedColumnName = "company_id",
        insertable = false,
        updatable = false
    )
    var jobs: MutableList<JobEntity> = ArrayList()

    @ManyToOne(cascade = [(CascadeType.MERGE)])
    @JoinColumn(
        name = "domain",
        referencedColumnName = "domain",
        insertable = true,
        updatable = true
    )
    lateinit var domain: DomainEntity

    @ManyToOne(cascade = [(CascadeType.MERGE)])
    @JoinColumn(
        name = "location",
        referencedColumnName = "location",
        insertable = true,
        updatable = true
    )
    lateinit var location: LocationEntity
}
