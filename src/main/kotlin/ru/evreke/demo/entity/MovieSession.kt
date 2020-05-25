package ru.evreke.demo.entity

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "movie_sessions")
data class MovieSession(
    var startedAt: LocalDateTime?,
    var endedAt: LocalDateTime?,
    var startSellingAt: LocalDateTime?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    var movie: Movie? = null

    @OneToOne
    @JoinColumn(name = "hall_id", referencedColumnName = "id")
    var hall: Hall? = null
    var occupancy: Int = 0
    var booked: Int = 0
    var privileged: Boolean = true
    var price: BigDecimal? = BigDecimal.ZERO
}