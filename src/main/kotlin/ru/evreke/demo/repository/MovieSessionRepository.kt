package ru.evreke.demo.repository

import org.springframework.data.repository.CrudRepository
import ru.evreke.demo.entity.MovieSession

interface MovieSessionRepository : CrudRepository<MovieSession, Long>