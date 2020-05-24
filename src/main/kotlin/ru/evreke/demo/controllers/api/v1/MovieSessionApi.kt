package ru.evreke.demo.controllers.api.v1

import org.springframework.web.bind.annotation.*
import ru.evreke.demo.entity.MovieSession
import ru.evreke.demo.exceptions.NotFoundException
import ru.evreke.demo.repository.MovieRepository
import ru.evreke.demo.repository.MovieSessionRepository

@RestController
@RequestMapping("/api/v1/movie-sessions")
class MovieSessionApi(
    private val repo: MovieSessionRepository,
    private val movieRepo: MovieRepository
) {
    @GetMapping("/", "")
    fun getAllSessions(): MutableIterable<MovieSession> {
        return repo.findAll()
    }

    @PostMapping("/", "")
    fun createSession(
        @RequestParam movieId: Long,
        @RequestBody session: MovieSession
    ) {
        val movie = movieRepo.findById(movieId).orElseThrow { NotFoundException("Movie with id=$movieId not found") }
        repo.save(session.also { it.movie = movie })
    }

    @PutMapping("/{id}")
    fun editSession(
        @PathVariable id: Long,
        @RequestParam movieId: Long,
        @RequestBody session: MovieSession
    ) {
        val newMovie = movieRepo.findById(movieId).orElseThrow { NotFoundException("Movie with id=$movieId not found") }
        repo.findById(id).orElseThrow { NotFoundException("Session with id=$id not found") }.apply {
            session.startedAt?.let { startedAt = it }
            session.endedAt?.let { endedAt = it }
            session.movie?.let { movie = newMovie }
        }
    }

    @DeleteMapping("/{id}")
    fun deleteSession(
        @PathVariable id: Long
    ) {
        repo.deleteById(id)
    }
}