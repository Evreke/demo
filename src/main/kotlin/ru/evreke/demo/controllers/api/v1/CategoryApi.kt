package ru.evreke.demo.controllers.api.v1

import org.springframework.web.bind.annotation.*
import ru.evreke.demo.entity.Category
import ru.evreke.demo.services.interfaces.CategoryService

@RestController
@RequestMapping("/api/v1/categories")
class CategoryApi(
    private val categoryService: CategoryService
) {
    @GetMapping("/", "")
    fun getAllCategories(): MutableIterable<Category> {
        return categoryService.getAllCategories()
    }

    @PostMapping("/", "")
    fun createCategory(
        @RequestBody category: Category
    ) {
        categoryService.createCategory(category)
    }

    @PutMapping("/{id}", "")
    fun editCategory(
        @PathVariable id: Long,
        @RequestBody category: Category
    ) {
        categoryService.updateCategory(id, category)
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(
        @PathVariable id: Long
    ) {
        categoryService.deleteCategory(id)
    }
}