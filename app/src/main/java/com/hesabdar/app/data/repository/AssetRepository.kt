package com.hesabdar.app.data.repository

import com.hesabdar.app.data.Asset
import com.hesabdar.app.data.dao.AssetDao
import kotlinx.coroutines.flow.Flow

class AssetRepository(private val assetDao: AssetDao) {
    fun getAll(): Flow<List<Asset>> = assetDao.getAll()
    fun getByType(type: String): Flow<List<Asset>> = assetDao.getByType(type)
    fun getTotalValue(): Flow<Long> = assetDao.getTotalValue()

    suspend fun getById(id: Long): Asset? = assetDao.getById(id)
    suspend fun addAsset(asset: Asset): Long = assetDao.insert(asset)
    suspend fun updateAsset(asset: Asset) = assetDao.update(asset)
    suspend fun deleteAsset(asset: Asset) = assetDao.delete(asset)
}
