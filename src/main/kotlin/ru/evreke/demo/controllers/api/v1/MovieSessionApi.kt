package ru.evreke.demo.controllers.api.v1

import org.springframework.web.bind.annotation.*
import ru.evreke.demo.entity.MovieSession
import ru.evreke.demo.exceptions.NotFoundException
import ru.evreke.demo.repository.HallRepository
import ru.evreke.demo.repository.MovieRepository
import ru.evreke.demo.repository.MovieSessionRepository

@RestController
@RequestMapping("/api/v1/movie-sessions")
class MovieSessionApi(
    private val repo: MovieSessionRepository,
    private val movieRepo: MovieRepository,
    private val hallRepo: HallRepository
) {
    @GetMapping("/", "")
    fun getAllSessions(): MutableIterable<MovieSession> {
        return repo.findAll()
    }

    @PostMapping("/", "")
    fun createSession(
        @RequestParam movieId: Long,
        @RequestParam hallId: Long,
        @RequestBody session: MovieSession
    ) {
        val movie = movieRepo.findById(movieId).orElseThrow { NotFoundException("Movie with id=$movieId not found") }
        val hall = hallRepo.findById(movieId).orElseThrow { NotFoundException("Hall with id=$hallId not found") }
        session.also {
            it.movie = movie
            it.hall = hall
            repo.save(it)
        }
    }

    @PutMapping("/{id}")
    fun editSession(
        @PathVariable id: Long,
        @RequestParam(required = false) movieId: Long?,
        @RequestParam(required = false) hallId: Long?,
        @RequestBody session: MovieSession
    ) {
        val newMovie = movieId?.let { movieRepo.findById(it).orElseThrow { NotFoundException("Movie with id=$it not found") } }
        val newHall = hallId?.let { hallRepo.findById(it).orElseThrow { NotFoundException("Hall with id=$hallId not found") } }
        repo.findById(id).orElseThrow { NotFoundException("Session with id=$id not found") }.apply {
            session.startedAt?.let { startedAt = it }
            session.endedAt?.let { endedAt = it }
            newMovie?.let { movie = it }
            newHall?.let { hall = it }
            repo.save(this)
        }
    }

    @PutMapping("/{id}/privilege")
    fun setPrivilege(
        @PathVariable id: Long,
        @RequestParam privileged: Boolean
    ) {
        repo.findById(id).orElseThrow { NotFoundException("Session with id=$id not found") }.also {
            it.privileged = privileged
            repo.save(it)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteSession(
        @PathVariable id: Long
    ) {
        repo.deleteById(id)
    }
}