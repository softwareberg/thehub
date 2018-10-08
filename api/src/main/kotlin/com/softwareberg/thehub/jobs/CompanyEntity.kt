package com.softwareberg.thehub.jobs

import java.util.ArrayList
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
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

    @OneToMany
    @JoinColumn(
        name = "company_id",
        referencedColumnName = "company_id",
        insertable = true,
        updatable = true
    )
    var jobs: MutableList<JobEntity> = ArrayList()
}
