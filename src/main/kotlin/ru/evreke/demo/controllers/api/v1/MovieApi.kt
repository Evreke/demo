package ru.evreke.demo.controllers.api.v1

import org.springframework.web.bind.annotation.*
import ru.evreke.demo.entity.Movie
import ru.evreke.demo.services.interfaces.MovieService

@RestController
@RequestMapping("/api/v1/movies")
class MovieApi(
    private val movieService: MovieService
) {
    @GetMapping("/", "")
    fun getAllMovies(): MutableIterable<Movie> {
        return movieService.getAllMovies()
    }

    @GetMapping("/{id}")
    fun getMovie(
        @PathVariable id: Long
    ): Movie {
        return movieService.getMovie(id)
    }

    @PostMapping("/", "")
    fun createMovie(
        @RequestBody movie: Movie
    ) {
        movieService.createMovie(movie)
    }

    @PutMapping("/{id}")
    fun editMovie(
        @PathVariable id: Long,
        @RequestBody movie: Movie
    ) {
        movieService.updateMovie(id, movie)
    }

    @DeleteMapping("/{id}")
    fun deleteMovie(
        @PathVariable id: Long
    ) {
        movieService.deleteMovie(id)
    }

}