package com.hesabdar.app.data.repository

import com.hesabdar.app.data.Budget
import com.hesabdar.app.data.dao.BudgetDao
import kotlinx.coroutines.flow.Flow

class BudgetRepository(private val budgetDao: BudgetDao) {
    fun getByMonth(year: Int, month: Int): Flow<List<Budget>> =
        budgetDao.getByMonth(year, month)

    suspend fun getSpecific(year: Int, month: Int, categoryId: Long?): Budget? =
        budgetDao.getSpecific(year, month, categoryId)

    suspend fun addBudget(budget: Budget): Long = budgetDao.insert(budget)
    suspend fun updateBudget(budget: Budget) = budgetDao.update(budget)
    suspend fun deleteBudget(budget: Budget) = budgetDao.delete(budget)
}
