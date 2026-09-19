package com.hesabdar.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hesabdar.app.ui.components.MoreMenuItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("بیشتر", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(
                    "مدیریت مالی",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            item {
                MoreMenuItem(
                    title = "بودجه",
                    subtitle = "سقف هزینه ماهانه برای هر دسته",
                    icon = Icons.Outlined.PieChart
                )
            }
            item {
                MoreMenuItem(
                    title = "تعهدات",
                    subtitle = "بدهی، قسط، وام و طلب",
                    icon = Icons.Outlined.Schedule
                )
            }
            item {
                MoreMenuItem(
                    title = "دارایی‌ها",
                    subtitle = "طلا، ارز، کریپتو و سایر",
                    icon = Icons.Outlined.Savings
                )
            }
            item {
                MoreMenuItem(
                    title = "دسته‌بندی‌ها",
                    subtitle = "مدیریت درآمد و هزینه",
                    icon = Icons.Outlined.Category
                )
            }

            item {
                Spacer(Modifier.height(12.dp))
                Text(
                    "تنظیمات",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            item {
                MoreMenuItem(
                    title = "واحد پول",
                    subtitle = "نمایش به تومان یا ریال",
                    icon = Icons.Outlined.AccountBalance
                )
            }
            item {
                MoreMenuItem(
                    title = "ظاهر",
                    subtitle = "روشن / تاریک / سیستم",
                    icon = Icons.Outlined.DarkMode
                )
            }
            item {
                MoreMenuItem(
                    title = "درباره حسابدار",
                    subtitle = "نسخه ۱.۰.۰",
                    icon = Icons.Outlined.Info
                )
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}
