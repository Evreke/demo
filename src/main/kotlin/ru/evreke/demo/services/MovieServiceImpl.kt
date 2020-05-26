package ru.evreke.demo.services

import javassist.NotFoundException
import org.springframework.stereotype.Service
import ru.evreke.demo.entity.Movie
import ru.evreke.demo.services.interfaces.MovieService
import ru.evreke.demo.repository.MovieRepository

@Service
class MovieServiceImpl(
    private val repo: MovieRepository
) : MovieService {
    override fun createMovie(movie: Movie) {
        repo.save(movie)
    }

    override fun getMovie(id: Long): Movie {
        return repo.findById(id).orElseThrow { NotFoundException("Movie with id=$id was not found") }
    }

    override fun getAllMovies(): MutableIterable<Movie> {
        return repo.findAll()
    }

    override fun deleteMovie(id: Long) {
        repo.deleteById(id)
    }

    override fun updateMovie(id: Long, movie: Movie) {
        repo.save(
            getMovie(id).apply {
                movie.duration?.let { duration = it }
                movie.title?.let { title = it }
            }
        )
    }
}