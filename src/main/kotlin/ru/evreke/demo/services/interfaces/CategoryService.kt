package ru.evreke.demo.services.interfaces

import ru.evreke.demo.entity.Category

interface CategoryService {
    fun createCategory(category: Category): Category
    fun getCategory(id: Long): Category
    fun getAllCategories(): MutableIterable<Category>
    fun deleteCategory(id: Long)
    fun updateCategory(id: Long, category: Category)
}