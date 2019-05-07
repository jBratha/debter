package net.ddns.jaronsky.debter.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table
class Customer(
    var firstName: String = "",
    var lastName: String = "",
    @Id @GeneratedValue
    var id: Long = 0
)