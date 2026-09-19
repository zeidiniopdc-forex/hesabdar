package com.hesabdar.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hesabdar.app.data.Account
import com.hesabdar.app.data.Transaction
import com.hesabdar.app.data.repository.AccountRepository
import com.hesabdar.app.data.repository.TransactionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class DashboardViewModel(
    accountRepository: AccountRepository,
    transactionRepository: TransactionRepository
) : ViewModel() {

    val totalBalance: StateFlow<Long> = accountRepository.getTotalBalance()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)

    val accounts: StateFlow<List<Account>> = accountRepository.getActiveAccounts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val recentTransactions: StateFlow<List<Transaction>> = transactionRepository.getRecent(15)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}

class DashboardViewModelFactory(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(accountRepository, transactionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
