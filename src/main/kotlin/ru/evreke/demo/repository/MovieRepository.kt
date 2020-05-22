package ru.evreke.demo.repository

import org.springframework.data.repository.CrudRepository
import ru.evreke.demo.entity.Movie

interface MovieRepository : CrudRepository<Movie, Long>