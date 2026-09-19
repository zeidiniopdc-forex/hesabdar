package com.hesabdar.app

import android.app.Application
import com.hesabdar.app.data.HesabdarDatabase
import com.hesabdar.app.data.repository.AccountRepository
import com.hesabdar.app.data.repository.AssetRepository
import com.hesabdar.app.data.repository.BudgetRepository
import com.hesabdar.app.data.repository.CategoryRepository
import com.hesabdar.app.data.repository.ObligationRepository
import com.hesabdar.app.data.repository.TransactionRepository

class HesabdarApplication : Application() {

    val database: HesabdarDatabase by lazy {
        HesabdarDatabase.getInstance(this)
    }

    val accountRepository: AccountRepository by lazy {
        AccountRepository(database.accountDao())
    }

    val categoryRepository: CategoryRepository by lazy {
        CategoryRepository(database.categoryDao())
    }

    val transactionRepository: TransactionRepository by lazy {
        TransactionRepository(
            database = database,
            transactionDao = database.transactionDao(),
            accountDao = database.accountDao(),
            budgetDao = database.budgetDao()
        )
    }

    val budgetRepository: BudgetRepository by lazy {
        BudgetRepository(database.budgetDao())
    }

    val obligationRepository: ObligationRepository by lazy {
        ObligationRepository(database.obligationDao())
    }

    val assetRepository: AssetRepository by lazy {
        AssetRepository(database.assetDao())
    }
}
