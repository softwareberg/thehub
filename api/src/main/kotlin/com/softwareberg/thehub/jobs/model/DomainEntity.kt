package com.softwareberg.thehub.jobs.model

import java.util.ArrayList
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "domains")
class DomainEntity {

    @Id
    @Column(name = "domain", nullable = false)
    lateinit var domain: String

    @OneToMany
    @JoinColumn(
        name = "domain",
        referencedColumnName = "domain",
        insertable = false,
        updatable = false
    )
    val companies: MutableList<CompanyEntity> = ArrayList()
}
