package com.softwareberg.thehub.jobs.model

import java.util.ArrayList
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "locations")
class LocationEntity {

    @Id
    @Column(name = "location", nullable = false)
    lateinit var location: String

    @OneToMany
    @JoinColumn(
        name = "location",
        referencedColumnName = "location",
        insertable = false,
        updatable = false
    )
    var companies: MutableList<CompanyEntity> = ArrayList()
}
