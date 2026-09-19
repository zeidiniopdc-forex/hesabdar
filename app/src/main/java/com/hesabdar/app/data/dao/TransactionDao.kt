package com.hesabdar.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hesabdar.app.data.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY date DESC, createdAt DESC")
    fun getAll(): Flow<List<Transaction>>

    @Query("""
        SELECT * FROM transactions
        WHERE date BETWEEN :from AND :to
        ORDER BY date DESC, createdAt DESC
    """)
    fun getByDateRange(from: Long, to: Long): Flow<List<Transaction>>

    @Query("""
        SELECT * FROM transactions
        WHERE accountId = :accountId OR toAccountId = :accountId
        ORDER BY date DESC
    """)
    fun getByAccount(accountId: Long): Flow<List<Transaction>>

    @Query("""
        SELECT * FROM transactions
        WHERE categoryId = :categoryId
        ORDER BY date DESC
    """)
    fun getByCategory(categoryId: Long): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getById(id: Long): Transaction?

    @Query("""
        SELECT COALESCE(SUM(amount), 0) FROM transactions
        WHERE type = :type AND date BETWEEN :from AND :to
    """)
    suspend fun sumByType(type: String, from: Long, to: Long): Long

    @Query("""
        SELECT COALESCE(SUM(ABS(amount)), 0) FROM transactions
        WHERE type = 'expense' AND categoryId = :categoryId
        AND date BETWEEN :from AND :to
    """)
    suspend fun sumExpenseByCategory(categoryId: Long, from: Long, to: Long): Long

    @Query("SELECT * FROM transactions ORDER BY date DESC, createdAt DESC LIMIT :limit")
    fun getRecent(limit: Int): Flow<List<Transaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction): Long

    @Update
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteById(id: Long)
}
