package com.hesabdar.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hesabdar.app.data.dao.AccountDao
import com.hesabdar.app.data.dao.AssetDao
import com.hesabdar.app.data.dao.BudgetDao
import com.hesabdar.app.data.dao.CategoryDao
import com.hesabdar.app.data.dao.ObligationDao
import com.hesabdar.app.data.dao.TransactionDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Account::class,
        Category::class,
        Transaction::class,
        Budget::class,
        Obligation::class,
        Asset::class
    ],
    version = 1,
    exportSchema = false
)
abstract class HesabdarDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
    abstract fun budgetDao(): BudgetDao
    abstract fun obligationDao(): ObligationDao
    abstract fun assetDao(): AssetDao

    companion object {
        @Volatile
        private var INSTANCE: HesabdarDatabase? = null

        fun getInstance(context: Context): HesabdarDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    HesabdarDatabase::class.java,
                    "hesabdar.db"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            INSTANCE?.let { database ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    seedDefaultCategories(database.categoryDao())
                                    seedDefaultAccount(database.accountDao())
                                }
                            }
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }

        private suspend fun seedDefaultCategories(dao: CategoryDao) {
            if (dao.count() > 0) return
            val defaults = listOf(
                Category(name = "حقوق", type = "income", icon = "salary", color = 0xFF4CAF50),
                Category(name = "فروش", type = "income", icon = "sell", color = 0xFF8BC34A),
                Category(name = "سایر درآمد", type = "income", icon = "other", color = 0xFFCDDC39),
                Category(name = "خوراک", type = "expense", icon = "food", color = 0xFFFF5722),
                Category(name = "حمل‌ونقل", type = "expense", icon = "transport", color = 0xFF2196F3),
                Category(name = "مسکن", type = "expense", icon = "home", color = 0xFF9C27B0),
                Category(name = "قبوض", type = "expense", icon = "bill", color = 0xFFFF9800),
                Category(name = "سرگرمی", type = "expense", icon = "fun", color = 0xFFE91E63),
                Category(name = "سلامت", type = "expense", icon = "health", color = 0xFF00BCD4),
                Category(name = "سایر هزینه", type = "expense", icon = "other", color = 0xFF607D8B)
            )
            dao.insertAll(defaults)
        }

        private suspend fun seedDefaultAccount(dao: AccountDao) {
            dao.insert(
                Account(
                    name = "نقد",
                    type = "cash",
                    balance = 0,
                    currency = "IRR",
                    color = 0xFF1976D2
                )
            )
        }
    }
}
