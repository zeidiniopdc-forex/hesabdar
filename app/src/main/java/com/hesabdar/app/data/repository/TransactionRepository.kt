package com.hesabdar.app.data.repository

import androidx.room.withTransaction
import com.hesabdar.app.data.HesabdarDatabase
import com.hesabdar.app.data.Transaction
import com.hesabdar.app.data.dao.AccountDao
import com.hesabdar.app.data.dao.BudgetDao
import com.hesabdar.app.data.dao.TransactionDao
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

class TransactionRepository(
    private val database: HesabdarDatabase,
    private val transactionDao: TransactionDao,
    private val accountDao: AccountDao,
    private val budgetDao: BudgetDao
) {
    fun getAll(): Flow<List<Transaction>> = transactionDao.getAll()
    fun getRecent(limit: Int = 20): Flow<List<Transaction>> = transactionDao.getRecent(limit)
    fun getByDateRange(from: Long, to: Long): Flow<List<Transaction>> =
        transactionDao.getByDateRange(from, to)
    fun getByAccount(accountId: Long): Flow<List<Transaction>> =
        transactionDao.getByAccount(accountId)
    fun getByCategory(categoryId: Long): Flow<List<Transaction>> =
        transactionDao.getByCategory(categoryId)

    suspend fun getById(id: Long): Transaction? = transactionDao.getById(id)

    suspend fun sumByType(type: String, from: Long, to: Long): Long =
        transactionDao.sumByType(type, from, to)

    /**
     * ثبت تراکنش + به‌روزرسانی موجودی حساب + آپدیت بودجه (در صورت هزینه)
     */
    suspend fun addTransaction(tx: Transaction): Long = database.withTransaction {
        val id = transactionDao.insert(tx)
        applyBalanceChange(tx, reverse = false)
        if (tx.type == "expense" && tx.categoryId != null) {
            updateBudgetSpent(tx.categoryId, tx.date, tx.amount)
        }
        id
    }

    suspend fun deleteTransaction(tx: Transaction) = database.withTransaction {
        applyBalanceChange(tx, reverse = true)
        if (tx.type == "expense" && tx.categoryId != null) {
            updateBudgetSpent(tx.categoryId, tx.date, -tx.amount)
        }
        transactionDao.delete(tx)
    }

    suspend fun updateTransaction(old: Transaction, new: Transaction) = database.withTransaction {
        // برگرداندن اثر قبلی
        applyBalanceChange(old, reverse = true)
        if (old.type == "expense" && old.categoryId != null) {
            updateBudgetSpent(old.categoryId, old.date, -old.amount)
        }
        // اعمال اثر جدید
        transactionDao.update(new)
        applyBalanceChange(new, reverse = false)
        if (new.type == "expense" && new.categoryId != null) {
            updateBudgetSpent(new.categoryId, new.date, new.amount)
        }
    }

    private suspend fun applyBalanceChange(tx: Transaction, reverse: Boolean) {
        val sign = if (reverse) -1 else 1
        when (tx.type) {
            "income" -> accountDao.updateBalance(tx.accountId, sign * tx.amount)
            "expense" -> accountDao.updateBalance(tx.accountId, -sign * tx.amount)
            "transfer" -> {
                accountDao.updateBalance(tx.accountId, -sign * tx.amount)
                tx.toAccountId?.let { accountDao.updateBalance(it, sign * tx.amount) }
            }
        }
    }

    private suspend fun updateBudgetSpent(categoryId: Long, date: Long, delta: Long) {
        val cal = Calendar.getInstance().apply { timeInMillis = date }
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1
        budgetDao.getSpecific(year, month, categoryId)?.let { budget ->
            val newSpent = (budget.spent + delta).coerceAtLeast(0)
            budgetDao.updateSpent(budget.id, newSpent)
        }
    }
}
