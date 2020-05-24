package ru.evreke.demo.entity

import javax.persistence.*

@Entity
@Table(name = "categories")
data class Category(
    var title: String?,
    var discount: Double?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}