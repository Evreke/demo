package ru.evreke.demo.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import ru.evreke.demo.entity.MovieSession
import ru.evreke.demo.entity.MovieSessionDto

interface MovieSessionRepository : CrudRepository<MovieSession, Long> {
    @Query(value = "select new ru.evreke.demo.entity.MovieSessionDto(ms.id, ms.date, ms.startedAt, ms.endedAt, ms.movie.id) from MovieSession as ms inner join ms.users as u where u.id = ?1")
    fun findSessionsForUser(id: Long): List<MovieSessionDto>
}