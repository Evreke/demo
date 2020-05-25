package ru.evreke.demo.repository

import org.springframework.data.repository.CrudRepository
import ru.evreke.demo.entity.Hall

interface HallRepository : CrudRepository<Hall, Long>