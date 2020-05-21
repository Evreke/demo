package ru.evreke.demo.entity

import javax.persistence.*

@Entity
@Table(name = "roles")
data class Role(
    var title: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @OneToMany(mappedBy = "role")
    var user: List<User>? = null
}