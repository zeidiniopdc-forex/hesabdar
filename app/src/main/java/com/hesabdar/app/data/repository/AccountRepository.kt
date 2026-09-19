package com.hesabdar.app.data.repository

import com.hesabdar.app.data.Account
import com.hesabdar.app.data.dao.AccountDao
import kotlinx.coroutines.flow.Flow

class AccountRepository(private val accountDao: AccountDao) {
    fun getActiveAccounts(): Flow<List<Account>> = accountDao.getActiveAccounts()
    fun getAllAccounts(): Flow<List<Account>> = accountDao.getAllAccounts()
    fun getTotalBalance(): Flow<Long> = accountDao.getTotalBalance()

    suspend fun getById(id: Long): Account? = accountDao.getById(id)
    suspend fun addAccount(account: Account): Long = accountDao.insert(account)
    suspend fun updateAccount(account: Account) = accountDao.update(account)
    suspend fun archiveAccount(id: Long) = accountDao.archive(id)
    suspend fun deleteAccount(account: Account) = accountDao.delete(account)
}
