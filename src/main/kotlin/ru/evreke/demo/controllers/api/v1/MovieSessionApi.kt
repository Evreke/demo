package ru.evreke.demo.controllers.api.v1

import org.springframework.http.ResponseEntity
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
        @RequestBody session: MovieSession
    ) {
        repo.findById(id).orElseThrow { NotFoundException("Session with id=$id not found") }.apply {
            startedAt = session.startedAt
            endedAt = session.endedAt
            movie = session.movie
        }
    }

    @DeleteMapping("/{id}")
    fun deleteSession(
        @PathVariable id: Long
    ) {
        repo.deleteById(id)
    }
}