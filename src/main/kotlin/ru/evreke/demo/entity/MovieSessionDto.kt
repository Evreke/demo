package ru.evreke.demo.entity

import java.time.LocalDate
import java.time.LocalTime

data class MovieSessionDto(
    var id: Long,
    var date: LocalDate,
    var startedAt: LocalTime,
    var endedAt: LocalTime,
    var movieId: Long
)