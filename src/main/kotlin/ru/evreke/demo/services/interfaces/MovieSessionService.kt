package ru.evreke.demo.services.interfaces

import ru.evreke.demo.entity.Hall
import ru.evreke.demo.entity.Movie
import ru.evreke.demo.entity.MovieSession

interface MovieSessionService {
    fun createMovieSession(movieSession: MovieSession, movie: Movie, hall: Hall): MovieSession
    fun getMovieSession(id: Long): MovieSession
    fun getUserMovieSessions(userId: Long): MutableIterable<MovieSession>
    fun getAllMovieSessions(): MutableIterable<MovieSession>
    fun updateMovieSession(movieSession: MovieSession)
    fun updateMovieSession(id: Long, movieSession: MovieSession, movieId: Long?, hallId: Long?)
    fun deleteMovieSession(id: Long)
    fun setPrivilege(id: Long, isPrivileged: Boolean)
}