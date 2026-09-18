package com.hesabdar.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val type: String = "cash", // cash, bank, card, wallet
    val balance: Long = 0, // in Rials
    val currency: String = "IRR",
    val color: Long = 0xFF1976D2,
    val isArchived: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val type: String, // income, expense
    val icon: String = "category",
    val color: Long = 0xFF9C27B0,
    val parentId: Long? = null
)

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = Account::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("accountId"), Index("categoryId"), Index("date")]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Long, // positive for income, negative for expense or absolute + type
    val type: String, // income, expense, transfer
    val accountId: Long,
    val toAccountId: Long? = null, // for transfers
    val categoryId: Long? = null,
    val description: String = "",
    val date: Long = System.currentTimeMillis(),
    val createdAt: Long = System.currentTimeMillis(),
    val isFromSms: Boolean = false,
    val smsBody: String? = null
)

@Entity(tableName = "budgets")
data class Budget(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val categoryId: Long? = null, // null = overall
    val amount: Long, // monthly limit in Rials
    val month: Int, // 1-12
    val year: Int,
    val spent: Long = 0
)

@Entity(tableName = "obligations")
data class Obligation(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val type: String, // debt, loan, installment, bill, receivable
    val amount: Long,
    val remaining: Long,
    val dueDate: Long,
    val isPaid: Boolean = false,
    val isReceivable: Boolean = false, // true = باید دریافت کنی
    val note: String = ""
)

@Entity(tableName = "assets")
data class Asset(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val type: String, // cash, gold, currency, crypto, other
    val quantity: Double = 1.0,
    val unitValue: Long, // value per unit in Rials
    val totalValue: Long,
    val note: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)
