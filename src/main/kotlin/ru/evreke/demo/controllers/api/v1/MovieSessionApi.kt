package ru.evreke.demo.controllers.api.v1

import org.springframework.web.bind.annotation.*
import ru.evreke.demo.entity.MovieSession
import ru.evreke.demo.services.interfaces.HallService
import ru.evreke.demo.services.interfaces.MovieService
import ru.evreke.demo.services.interfaces.MovieSessionService
import ru.evreke.demo.services.interfaces.UserService

@RestController
@RequestMapping("/api/v1/movie-sessions")
class MovieSessionApi(
    private val movieSessionService: MovieSessionService,
    private val movieService: MovieService,
    private val hallService: HallService
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
        val movie = movieService.getMovie(movieId)
        val hall = hallService.getHall(hallId)
        movieSessionService.createMovieSession(session, movie, hall)
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