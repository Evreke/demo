package ru.evreke.demo.controllers.api.v1

import org.springframework.web.bind.annotation.*
import ru.evreke.demo.entity.Category
import ru.evreke.demo.exceptions.NotFoundException
import ru.evreke.demo.repository.CategoryRepository

@RestController
@RequestMapping("/api/v1/categories")
class CategoryApi(
    private val repo: CategoryRepository
) {
    @GetMapping("/", "")
    fun getAllCategories(): MutableIterable<Category> {
        return repo.findAll()
    }

    @PostMapping("/", "")
    fun createCategory(
        @RequestBody category: Category
    ) {
        repo.save(category)
    }

    @PutMapping("/{id}", "")
    fun editCategory(
        @PathVariable id: Long,
        @RequestBody category: Category
    ) {
        repo.findById(id).orElseThrow { NotFoundException("Category with id=$id not found") }.apply {
            category.discount?.let { discount = it }
            category.title?.let { title = it }
            repo.save(this)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(
        @PathVariable id: Long
    ) {
        repo.deleteById(id)
    }
}