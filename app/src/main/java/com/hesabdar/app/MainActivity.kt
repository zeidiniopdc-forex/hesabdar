package com.hesabdar.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hesabdar.app.ui.DashboardViewModel
import com.hesabdar.app.ui.DashboardViewModelFactory
import com.hesabdar.app.ui.theme.HesabdarTheme
import java.text.NumberFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val app = application as HesabdarApplication
        setContent {
            HesabdarTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DashboardScreen(
                        viewModel = viewModel(
                            factory = DashboardViewModelFactory(
                                app.accountRepository,
                                app.transactionRepository
                            )
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val totalBalance by viewModel.totalBalance.collectAsState(initial = 0L)
    val accounts by viewModel.accounts.collectAsState(initial = emptyList())
    val recentTx by viewModel.recentTransactions.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("حسابدار") })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("موجودی کل", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = formatRial(totalBalance),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            item {
                Text("حساب‌ها", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }

            items(accounts) { account ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(account.name, fontWeight = FontWeight.SemiBold)
                        Text(
                            formatRial(account.balance),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            account.type,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                Spacer(Modifier.height(8.dp))
                Text("آخرین تراکنش‌ها", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }

            if (recentTx.isEmpty()) {
                item {
                    Text(
                        "هنوز تراکنشی ثبت نشده",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                items(recentTx) { tx ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                tx.description.ifBlank { typeLabel(tx.type) },
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                formatRial(tx.amount),
                                color = when (tx.type) {
                                    "income" -> MaterialTheme.colorScheme.tertiary
                                    "expense" -> MaterialTheme.colorScheme.error
                                    else -> MaterialTheme.colorScheme.onSurface
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun formatRial(amount: Long): String {
    val nf = NumberFormat.getNumberInstance(Locale("fa", "IR"))
    return "${nf.format(amount)} ریال"
}

private fun typeLabel(type: String): String = when (type) {
    "income" -> "درآمد"
    "expense" -> "هزینه"
    "transfer" -> "انتقال"
    else -> type
}
