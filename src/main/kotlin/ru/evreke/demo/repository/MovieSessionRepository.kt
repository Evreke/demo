package ru.evreke.demo.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import ru.evreke.demo.entity.MovieSession

interface MovieSessionRepository : CrudRepository<MovieSession, Long> {
    @Query(value = "SELECT * " +
        "FROM movie_sessions as ms " +
        "JOIN users_movie_sessions ums on ms.id = ums.movie_session_id " +
        "JOIN bookings as b on b.movie_session_id = ums.movie_session_id " +
        "WHERE b.payed = true and ums.user_id = ?1", nativeQuery = true)
    fun findSessionsByUserId(id: Long): List<MovieSession>
}