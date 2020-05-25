package ru.evreke.demo.entity

import javax.persistence.*

@Entity
@Table(name = "halls")
data class Hall(
    var name: String?,
    var capacity: Int?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}