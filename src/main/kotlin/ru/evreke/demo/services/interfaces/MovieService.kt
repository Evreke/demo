package ru.evreke.demo.services.interfaces

import ru.evreke.demo.entity.Movie

interface MovieService {
    fun createMovie(movie: Movie): Movie
    fun getMovie(id: Long): Movie
    fun getAllMovies(): MutableIterable<Movie>
    fun deleteMovie(id: Long)
    fun updateMovie(id: Long, movie: Movie)
}