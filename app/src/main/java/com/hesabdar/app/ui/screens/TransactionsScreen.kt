package com.hesabdar.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hesabdar.app.ui.components.EmptyState
import com.hesabdar.app.ui.components.TransactionRow

private data class TxListDemo(
    val title: String,
    val subtitle: String,
    val amount: Long,
    val type: String
)

private val allDemo = listOf(
    TxListDemo("حقوق ماهانه", "درآمد · ۱ شهریور", 85_000_0000, "income"),
    TxListDemo("اجاره خانه", "مسکن · ۱ شهریور", 25_000_0000, "expense"),
    TxListDemo("خرید سوپرمارکت", "خوراک · ۵ شهریور", 2_450_0000, "expense"),
    TxListDemo("فروش لپ‌تاپ", "فروش · ۸ شهریور", 18_000_0000, "income"),
    TxListDemo("بنزین", "حمل‌ونقل · ۱۰ شهریور", 1_200_0000, "expense"),
    TxListDemo("انتقال به پس‌انداز", "نقد → بانک · ۱۲ شهریور", 10_000_0000, "transfer"),
    TxListDemo("قبض برق", "قبوض · ۱۵ شهریور", 890_0000, "expense")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen() {
    var filter by remember { mutableStateOf("all") }
    val filtered = when (filter) {
        "income" -> allDemo.filter { it.type == "income" }
        "expense" -> allDemo.filter { it.type == "expense" }
        "transfer" -> allDemo.filter { it.type == "transfer" }
        else -> allDemo
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("تراکنش‌ها", fontWeight = FontWeight.Bold) },
                actions = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "جستجو",
                        modifier = Modifier.padding(end = 16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    "all" to "همه",
                    "income" to "درآمد",
                    "expense" to "هزینه",
                    "transfer" to "انتقال"
                ).forEach { (key, label) ->
                    FilterChip(
                        selected = filter == key,
                        onClick = { filter = key },
                        label = { Text(label) },
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (filtered.isEmpty()) {
                    item { EmptyState("تراکنشی در این فیلتر نیست") }
                } else {
                    items(filtered) { tx ->
                        TransactionRow(
                            title = tx.title,
                            subtitle = tx.subtitle,
                            amountRial = tx.amount,
                            type = tx.type
                        )
                    }
                }
                item { Spacer(Modifier.height(24.dp)) }
            }
        }
    }
}
