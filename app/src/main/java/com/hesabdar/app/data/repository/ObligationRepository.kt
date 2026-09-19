package com.hesabdar.app.data.repository

import com.hesabdar.app.data.Obligation
import com.hesabdar.app.data.dao.ObligationDao
import kotlinx.coroutines.flow.Flow

class ObligationRepository(private val obligationDao: ObligationDao) {
    fun getActive(): Flow<List<Obligation>> = obligationDao.getActive()
    fun getAll(): Flow<List<Obligation>> = obligationDao.getAll()
    fun getDebts(): Flow<List<Obligation>> = obligationDao.getByReceivable(false)
    fun getReceivables(): Flow<List<Obligation>> = obligationDao.getByReceivable(true)

    suspend fun getById(id: Long): Obligation? = obligationDao.getById(id)
    suspend fun addObligation(obligation: Obligation): Long = obligationDao.insert(obligation)
    suspend fun updateObligation(obligation: Obligation) = obligationDao.update(obligation)
    suspend fun markAsPaid(id: Long) = obligationDao.markAsPaid(id)
    suspend fun deleteObligation(obligation: Obligation) = obligationDao.delete(obligation)
}
