package ru.evreke.demo.repository

import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.repository.CrudRepository
import ru.evreke.demo.entity.Role

@EnableJpaRepositories
interface RoleRepository : CrudRepository<Role, Long>