package ru.evreke.demo.repository

import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.repository.CrudRepository
import ru.evreke.demo.entity.Category

@EnableJpaRepositories
interface CategoryRepository : CrudRepository<Category, Long>