package ru.evreke.demo.repository

import org.springframework.data.repository.CrudRepository
import ru.evreke.demo.entity.User

interface UserRepository : CrudRepository<User, Long>