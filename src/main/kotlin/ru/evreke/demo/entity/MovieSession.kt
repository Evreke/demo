package ru.evreke.demo.entity

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "movie_sessions")
data class MovieSession(
    @JsonFormat(pattern = "HH:mm")
    var startedAt: LocalTime?,
    @JsonFormat(pattern = "HH:mm")
    var endedAt: LocalTime?,
    @JsonFormat(pattern = "dd/MM/yyyy")
    var date: LocalDate?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    var movie: Movie? = null
}