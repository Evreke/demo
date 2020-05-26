package ru.evreke.demo.services

import javassist.NotFoundException
import org.springframework.stereotype.Service
import ru.evreke.demo.entity.MovieSession
import ru.evreke.demo.repository.MovieSessionRepository
import ru.evreke.demo.services.interfaces.HallService
import ru.evreke.demo.services.interfaces.MovieService
import ru.evreke.demo.services.interfaces.MovieSessionService

@Service
class MovieSessionServiceImpl(
    private val repo: MovieSessionRepository,
    private val movieService: MovieService,
    private val hallService: HallService
) : MovieSessionService {
    override fun createMovieSession(
        movieSession: MovieSession,
        movieId: Long,
        hallId: Long
    ) {
        repo.save(
            movieSession.also {
                it.movie = movieService.getMovie(movieId)
                it.hall = hallService.getHall(hallId)
            }
        )
    }

    override fun getMovieSession(id: Long): MovieSession {
        return repo.findById(id).orElseThrow { NotFoundException("Movie session with id=$id was not found") }
    }

    override fun getUserMovieSessions(userId: Long): MutableIterable<MovieSession> {
        return repo.findSessionsByUserId(userId)
    }

    override fun getAllMovieSessions(): MutableIterable<MovieSession> {
        return repo.findAll()
    }

    override fun updateMovieSession(movieSession: MovieSession) {
        repo.save(movieSession)
    }

    override fun updateMovieSession(
        id: Long,
        movieSession: MovieSession,
        movieId: Long?,
        hallId: Long?
    ) {
        val newMovie = movieId?.let { movieService.getMovie(it) }
        val newHall = hallId?.let { hallService.getHall(it) }
        repo.save(
            getMovieSession(id).apply {
                movieSession.startedAt?.let { startedAt = it }
                movieSession.endedAt?.let { endedAt = it }
                newMovie?.let { movie = it }
                newHall?.let { hall = it }
            }
        )
    }

    override fun deleteMovieSession(id: Long) {
        repo.deleteById(id)
    }

    override fun setPrivilege(id: Long, isPrivileged: Boolean) {
        getMovieSession(id).also {
            it.privileged = isPrivileged
            repo.save(it)
        }
    }
}