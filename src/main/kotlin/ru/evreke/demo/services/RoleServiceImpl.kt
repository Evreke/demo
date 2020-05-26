package ru.evreke.demo.services

import javassist.NotFoundException
import org.springframework.stereotype.Service
import ru.evreke.demo.entity.Role
import ru.evreke.demo.services.interfaces.RoleService
import ru.evreke.demo.repository.RoleRepository

@Service
class RoleServiceImpl(
    private val repo: RoleRepository
) : RoleService {
    override fun createRole(role: Role) {
        repo.save(role)
    }

    override fun getRole(id: Long): Role {
        return repo.findById(id).orElseThrow { NotFoundException("Role with id=$id was not found") }
    }

    override fun getAllRoles(): MutableIterable<Role> {
        return repo.findAll()
    }

    override fun deleteRole(id: Long) {
        repo.deleteById(id)
    }

    override fun updateRole(id: Long, role: Role) {
        repo.save(
            getRole(id).apply { title = role.title }
        )
    }
}