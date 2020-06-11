package com.example.incomeandexpensesapp.ui

import com.github.mikephil.charting.formatter.ValueFormatter


class BarChartValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val months = arrayOf(
            "January", "February",
            "March", "April", "May", "June", "July",
            "August", "September", "October", "November",
            "December"
        )

        return months[value.toInt()]
    }
}