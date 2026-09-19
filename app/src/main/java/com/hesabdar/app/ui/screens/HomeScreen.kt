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
import com.hesabdar.app.ui.components.BalanceHeroCard
import com.hesabdar.app.ui.components.EmptyState
import com.hesabdar.app.ui.components.SectionHeader
import com.hesabdar.app.ui.components.TransactionRow

/** داده نمایشی برای نمای کلی UI — بعداً به Room وصل می‌شود */
private data class HomeDemoTx(
    val title: String,
    val subtitle: String,
    val amount: Long,
    val type: String
)

private val demoTransactions = listOf(
    HomeDemoTx("حقوق ماهانه", "درآمد · امروز", 85_000_0000, "income"),
    HomeDemoTx("خرید سوپرمارکت", "خوراک · دیروز", 2_450_0000, "expense"),
    HomeDemoTx("بنزین", "حمل‌ونقل · ۲ روز پیش", 1_200_0000, "expense"),
    HomeDemoTx("انتقال به پس‌انداز", "نقد → بانک · ۳ روز پیش", 10_000_0000, "transfer")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "حسابدار",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* بعداً: افزودن تراکنش */ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "ثبت تراکنش")
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                BalanceHeroCard(
                    totalBalanceRial = 124_500_0000,
                    incomeThisMonth = 85_000_0000,
                    expenseThisMonth = 18_320_0000
                )
            }

            item {
                Spacer(Modifier.height(4.dp))
                SectionHeader(title = "آخرین تراکنش‌ها", actionLabel = "همه")
            }

            if (demoTransactions.isEmpty()) {
                item { EmptyState("هنوز تراکنشی ثبت نشده") }
            } else {
                items(demoTransactions) { tx ->
                    TransactionRow(
                        title = tx.title,
                        subtitle = tx.subtitle,
                        amountRial = tx.amount,
                        type = tx.type
                    )
                }
            }

            item { Spacer(Modifier.height(72.dp)) }
        }
    }
}
