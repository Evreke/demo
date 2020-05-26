package ru.evreke.demo.services.interfaces

import ru.evreke.demo.entity.User

interface UserService {
    fun getUser(id: Long): User
}