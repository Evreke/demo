package ru.evreke.demo.services

import javassist.NotFoundException
import org.springframework.stereotype.Service
import ru.evreke.demo.entity.Category
import ru.evreke.demo.services.interfaces.CategoryService
import ru.evreke.demo.repository.CategoryRepository

@Service
class CategoryServiceImpl(
    private val repo: CategoryRepository
) : CategoryService {
    override fun createCategory(category: Category): Category {
        return repo.save(category)
    }

    override fun getCategory(id: Long): Category {
        return repo.findById(id).orElseThrow { NotFoundException("Category with id=$id was not found") }
    }

    override fun getAllCategories(): MutableIterable<Category> {
        return repo.findAll()
    }

    override fun deleteCategory(id: Long) {
        repo.deleteById(id)
    }

    override fun updateCategory(id: Long, category: Category) {
        repo.save(
            getCategory(id).apply {
                category.discount?.let { discount = it }
                category.title?.let { title = it }

            }
        )
    }
}