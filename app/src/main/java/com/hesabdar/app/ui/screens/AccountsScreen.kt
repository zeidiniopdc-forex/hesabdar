package com.hesabdar.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hesabdar.app.ui.components.AccountCard
import com.hesabdar.app.ui.components.BalanceHeroCard

private data class DemoAccount(
    val name: String,
    val type: String,
    val balance: Long,
    val color: Long
)

private val demoAccounts = listOf(
    DemoAccount("نقد", "cash", 4_500_0000, 0xFF1976D2),
    DemoAccount("ملی · پس‌انداز", "bank", 95_000_0000, 0xFF00897B),
    DemoAccount("کارت سامان", "card", 18_200_0000, 0xFF6A1B9A),
    DemoAccount("کیف پول", "wallet", 6_800_0000, 0xFFF57C00)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen() {
    val total = demoAccounts.sumOf { it.balance }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("حساب‌ها", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* بعداً: افزودن حساب */ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "حساب جدید")
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                BalanceHeroCard(
                    totalBalanceRial = total,
                    incomeThisMonth = 0,
                    expenseThisMonth = 0
                )
            }
            item {
                Text(
                    "حساب‌های فعال",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            items(demoAccounts) { acc ->
                AccountCard(
                    name = acc.name,
                    type = acc.type,
                    balanceRial = acc.balance,
                    colorLong = acc.color
                )
            }
            item { Spacer(Modifier.height(72.dp)) }
        }
    }
}
