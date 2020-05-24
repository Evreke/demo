package ru.evreke.demo.controllers.api.v1

import org.springframework.web.bind.annotation.*
import ru.evreke.demo.entity.Movie
import ru.evreke.demo.exceptions.NotFoundException
import ru.evreke.demo.repository.MovieRepository

@RestController
@RequestMapping("/api/v1/movies")
class MovieApi(
    private val repo: MovieRepository
) {
    @GetMapping("/", "")
    fun getAllMovies(): MutableIterable<Movie> {
        return repo.findAll()
    }

    @GetMapping("/{id}")
    fun getMovie(
        @PathVariable id: Long
    ): Movie? {
        return repo.findById(id).orElseThrow { NotFoundException("Movie with id=$id not found") }
    }

    @PostMapping("/", "")
    fun createMovie(
        @RequestBody movie: Movie
    ) {
        repo.save(movie)
    }

    @PutMapping("/{id}")
    fun editMovie(
        @PathVariable id: Long,
        @RequestBody movie: Movie
    ) {
        repo.findById(id).orElseThrow { NotFoundException("Movie with id=$id not found") }.apply {
            movie.duration?.let { duration = it }
            movie.title?.let { title = it }
            repo.save(this)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteMovie(
        @PathVariable id: Long
    ) {
        repo.deleteById(id)
    }

}