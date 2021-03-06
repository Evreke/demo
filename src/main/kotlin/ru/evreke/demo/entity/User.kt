package ru.evreke.demo.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Column(unique = true)
    var username: String,
    var phoneNumber: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    val createdAt: LocalDateTime = LocalDateTime.now()

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    var role: Role? = null

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    var category: Category? = null

    @ManyToMany
    @JoinTable(
        name = "users_movie_sessions",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "movie_session_id")]
    )
    var sessions: MutableList<MovieSession> = mutableListOf()
}