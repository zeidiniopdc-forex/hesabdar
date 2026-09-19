package com.hesabdar.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hesabdar.app.data.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Query("SELECT * FROM accounts WHERE isArchived = 0 ORDER BY name")
    fun getActiveAccounts(): Flow<List<Account>>

    @Query("SELECT * FROM accounts ORDER BY name")
    fun getAllAccounts(): Flow<List<Account>>

    @Query("SELECT * FROM accounts WHERE id = :id")
    suspend fun getById(id: Long): Account?

    @Query("SELECT COALESCE(SUM(balance), 0) FROM accounts WHERE isArchived = 0")
    fun getTotalBalance(): Flow<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: Account): Long

    @Update
    suspend fun update(account: Account)

    @Query("UPDATE accounts SET balance = balance + :delta WHERE id = :id")
    suspend fun updateBalance(id: Long, delta: Long)

    @Query("UPDATE accounts SET isArchived = 1 WHERE id = :id")
    suspend fun archive(id: Long)

    @Delete
    suspend fun delete(account: Account)
}
