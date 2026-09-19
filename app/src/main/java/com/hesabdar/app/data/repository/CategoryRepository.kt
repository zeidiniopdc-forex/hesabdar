package com.hesabdar.app.data.repository

import com.hesabdar.app.data.Category
import com.hesabdar.app.data.dao.CategoryDao
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryDao: CategoryDao) {
    fun getByType(type: String): Flow<List<Category>> = categoryDao.getByType(type)
    fun getAll(): Flow<List<Category>> = categoryDao.getAll()

    suspend fun getById(id: Long): Category? = categoryDao.getById(id)
    suspend fun addCategory(category: Category): Long = categoryDao.insert(category)
    suspend fun updateCategory(category: Category) = categoryDao.update(category)
    suspend fun deleteCategory(category: Category) = categoryDao.delete(category)
}
