package com.hesabdar.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hesabdar.app.data.Obligation
import kotlinx.coroutines.flow.Flow

@Dao
interface ObligationDao {
    @Query("SELECT * FROM obligations WHERE isPaid = 0 ORDER BY dueDate ASC")
    fun getActive(): Flow<List<Obligation>>

    @Query("SELECT * FROM obligations ORDER BY dueDate DESC")
    fun getAll(): Flow<List<Obligation>>

    @Query("SELECT * FROM obligations WHERE isReceivable = :isReceivable AND isPaid = 0 ORDER BY dueDate ASC")
    fun getByReceivable(isReceivable: Boolean): Flow<List<Obligation>>

    @Query("SELECT * FROM obligations WHERE id = :id")
    suspend fun getById(id: Long): Obligation?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obligation: Obligation): Long

    @Update
    suspend fun update(obligation: Obligation)

    @Query("UPDATE obligations SET isPaid = 1, remaining = 0 WHERE id = :id")
    suspend fun markAsPaid(id: Long)

    @Delete
    suspend fun delete(obligation: Obligation)
}
