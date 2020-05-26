package ru.evreke.demo.controllers.api.v1

import org.springframework.web.bind.annotation.*
import ru.evreke.demo.entity.MovieSession
import ru.evreke.demo.services.interfaces.MovieSessionService

@RestController
@RequestMapping("/api/v1/movie-sessions")
class MovieSessionApi(
    private val movieSessionService: MovieSessionService
) {
    @GetMapping("/", "")
    fun getAllSessions(): MutableIterable<MovieSession> {
        return movieSessionService.getAllMovieSessions()
    }

    @PostMapping("/", "")
    fun createSession(
        @RequestParam movieId: Long,
        @RequestParam hallId: Long,
        @RequestBody session: MovieSession
    ) {
        movieSessionService.createMovieSession(session, movieId, hallId)
    }

    @PutMapping("/{id}")
    fun editSession(
        @PathVariable id: Long,
        @RequestParam(required = false) movieId: Long?,
        @RequestParam(required = false) hallId: Long?,
        @RequestBody session: MovieSession
    ) {
        movieSessionService.updateMovieSession(id, session, movieId, hallId)
    }

    @PutMapping("/{id}/privilege")
    fun setPrivilege(
        @PathVariable id: Long,
        @RequestParam isPrivileged: Boolean
    ) {
        movieSessionService.setPrivilege(id, isPrivileged)
    }

    @DeleteMapping("/{id}")
    fun deleteSession(
        @PathVariable id: Long
    ) {
        movieSessionService.deleteMovieSession(id)
    }
}