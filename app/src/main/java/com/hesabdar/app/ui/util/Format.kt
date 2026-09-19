package com.hesabdar.app.ui.util

import java.text.NumberFormat
import java.util.Locale

private val faLocale = Locale("fa", "IR")

fun formatRial(amount: Long, withUnit: Boolean = true): String {
    val nf = NumberFormat.getNumberInstance(faLocale)
    val formatted = nf.format(kotlin.math.abs(amount))
    return if (withUnit) "$formatted ریال" else formatted
}

fun formatToman(amountRial: Long, withUnit: Boolean = true): String {
    val toman = amountRial / 10
    val nf = NumberFormat.getNumberInstance(faLocale)
    val formatted = nf.format(kotlin.math.abs(toman))
    return if (withUnit) "$formatted تومان" else formatted
}

fun typeLabelFa(type: String): String = when (type) {
    "income" -> "درآمد"
    "expense" -> "هزینه"
    "transfer" -> "انتقال"
    else -> type
}

fun accountTypeLabelFa(type: String): String = when (type) {
    "cash" -> "نقد"
    "bank" -> "بانک"
    "card" -> "کارت"
    "wallet" -> "کیف پول"
    else -> type
}
