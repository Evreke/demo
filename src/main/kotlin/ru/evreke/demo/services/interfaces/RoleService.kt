package ru.evreke.demo.services.interfaces

import ru.evreke.demo.entity.Role

interface RoleService {
    fun createRole(role: Role)
    fun getRole(id: Long): Role
    fun getAllRoles(): MutableIterable<Role>
    fun deleteRole(id: Long)
    fun updateRole(id: Long, role: Role)
}