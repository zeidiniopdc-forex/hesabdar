package com.hesabdar.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hesabdar.app.data.Budget
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budgets WHERE year = :year AND month = :month")
    fun getByMonth(year: Int, month: Int): Flow<List<Budget>>

    @Query("""
        SELECT * FROM budgets
        WHERE year = :year AND month = :month AND categoryId IS :categoryId
    """)
    suspend fun getSpecific(year: Int, month: Int, categoryId: Long?): Budget?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budget: Budget): Long

    @Update
    suspend fun update(budget: Budget)

    @Query("UPDATE budgets SET spent = :spent WHERE id = :id")
    suspend fun updateSpent(id: Long, spent: Long)

    @Delete
    suspend fun delete(budget: Budget)
}
