package ru.evreke.demo.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "bookings")
data class Booking(
    var createdAt: LocalDateTime = LocalDateTime.now()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne
    @JoinColumn(name = "movie_session_id", nullable = false)
    var session: MovieSession? = null

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null
}