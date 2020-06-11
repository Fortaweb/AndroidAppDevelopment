package com.example.incomeandexpensesapp.ui

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


class AmountValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val otherSymbols = DecimalFormatSymbols(Locale.getDefault())
        otherSymbols.decimalSeparator = ','
        otherSymbols.groupingSeparator = '.'
        val amountFormat = DecimalFormat("#,###,##0.00", otherSymbols)

        return "€ " + amountFormat.format(value)
    }
}