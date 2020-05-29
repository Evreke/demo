package ru.evreke.demo.entity

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "movie_sessions")
data class MovieSession(
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    var startedAt: LocalDateTime?,
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    var endedAt: LocalDateTime?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    var movie: Movie? = null

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "hall_id", referencedColumnName = "id")
    var hall: Hall? = null
    var occupancy: Int = 0
    var booked: Int = 0
    var privileged: Boolean = true
    var price: BigDecimal? = BigDecimal.ZERO

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    var startSellingAt: LocalDateTime = LocalDateTime.MIN
}