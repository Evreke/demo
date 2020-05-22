package ru.evreke.demo.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import ru.evreke.demo.entity.MovieSession

interface MovieSessionRepository : CrudRepository<MovieSession, Long> {
    @Query(value = "select ms from MovieSession as ms inner join User as u on u.id = ?1")
    fun findSessionsByUserId(id: Long): List<MovieSession>
}