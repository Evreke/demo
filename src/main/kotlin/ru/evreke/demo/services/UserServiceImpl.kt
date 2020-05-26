package ru.evreke.demo.services

import javassist.NotFoundException
import org.springframework.stereotype.Service
import ru.evreke.demo.entity.User
import ru.evreke.demo.repository.UserRepository
import ru.evreke.demo.services.interfaces.UserService

@Service
class UserServiceImpl(
    private val repo: UserRepository
) : UserService {
    override fun getUser(id: Long): User {
        return repo.findById(id).orElseThrow { NotFoundException("User with id=$id was not found") }
    }
}