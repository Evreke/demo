package ru.evreke.demo.entity

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "categories")
data class Category(
    var title: String?,
    var discount: BigDecimal?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}