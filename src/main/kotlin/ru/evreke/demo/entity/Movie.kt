package ru.evreke.demo.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "movies")
data class Movie(
    var title: String,
    @JsonFormat(pattern = "HH:mm")
    var duration: LocalTime
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @JsonBackReference
    @OneToMany(mappedBy = "movie")
    var sessions: List<MovieSession>? = null
}