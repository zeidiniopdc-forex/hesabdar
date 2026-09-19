package com.hesabdar.app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hesabdar.app.ui.screens.AccountsScreen
import com.hesabdar.app.ui.screens.HomeScreen
import com.hesabdar.app.ui.screens.MoreScreen
import com.hesabdar.app.ui.screens.TransactionsScreen

sealed class TopLevelRoute(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    data object Home : TopLevelRoute("home", "خانه", Icons.Filled.Home, Icons.Outlined.Home)
    data object Transactions : TopLevelRoute(
        "transactions", "تراکنش‌ها",
        Icons.Filled.ReceiptLong, Icons.Outlined.ReceiptLong
    )
    data object Accounts : TopLevelRoute(
        "accounts", "حساب‌ها",
        Icons.Filled.AccountBalanceWallet, Icons.Outlined.AccountBalanceWallet
    )
    data object More : TopLevelRoute("more", "بیشتر", Icons.Filled.MoreHoriz, Icons.Outlined.MoreHoriz)
}

val topLevelRoutes = listOf(
    TopLevelRoute.Home,
    TopLevelRoute.Transactions,
    TopLevelRoute.Accounts,
    TopLevelRoute.More
)

@Composable
fun HesabdarApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                topLevelRoutes.forEach { item ->
                    val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                if (selected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.label
                            )
                        },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TopLevelRoute.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(TopLevelRoute.Home.route) { HomeScreen() }
            composable(TopLevelRoute.Transactions.route) { TransactionsScreen() }
            composable(TopLevelRoute.Accounts.route) { AccountsScreen() }
            composable(TopLevelRoute.More.route) { MoreScreen() }
        }
    }
}
