package com.hesabdar.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hesabdar.app.data.Asset
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {
    @Query("SELECT * FROM assets ORDER BY updatedAt DESC")
    fun getAll(): Flow<List<Asset>>

    @Query("SELECT * FROM assets WHERE type = :type ORDER BY updatedAt DESC")
    fun getByType(type: String): Flow<List<Asset>>

    @Query("SELECT COALESCE(SUM(totalValue), 0) FROM assets")
    fun getTotalValue(): Flow<Long>

    @Query("SELECT * FROM assets WHERE id = :id")
    suspend fun getById(id: Long): Asset?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asset: Asset): Long

    @Update
    suspend fun update(asset: Asset)

    @Delete
    suspend fun delete(asset: Asset)
}
