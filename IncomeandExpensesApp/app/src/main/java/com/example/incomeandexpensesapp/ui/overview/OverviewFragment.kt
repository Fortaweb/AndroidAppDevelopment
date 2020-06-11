package com.example.incomeandexpensesapp.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import com.example.incomeandexpensesapp.R
import com.example.incomeandexpensesapp.ui.BarChartValueFormatter
import com.example.incomeandexpensesapp.ui.AmountValueFormatter
import com.example.incomeandexpensesapp.ui.income.IncomeViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


class OverviewFragment : Fragment() {

    private val viewModel: IncomeViewModel by viewModels()
    private lateinit var overviewViewModel: OverviewViewModel
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        overviewViewModel =
                ViewModelProviders.of(this).get(OverviewViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_overview, container, false)

        initChart(root)

        return root
    }

    private fun initChart(root : View) {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                val cal: Calendar = Calendar.getInstance()
                val currentMonth = cal.get(Calendar.MONTH)

                val entriesGroup1 = ArrayList<BarEntry>()
                val entriesGroup2 = ArrayList<BarEntry>()
                for (i in currentMonth - 1..currentMonth + 1) {
                    entriesGroup1.add(BarEntry( i.toFloat(), viewModel.transactionRepository.getTotalAmountIncomeForMonth(String.format("%02d", i))))
                    entriesGroup2.add(BarEntry( i.toFloat(), viewModel.transactionRepository.getTotalAmountExpensesForMonth(String.format("%02d", i))))
                }

                val listColors = ArrayList<Int>()
                for (c in ColorTemplate.VORDIPLOM_COLORS) {
                    listColors.add(c)
                }

                val set1 = BarDataSet(entriesGroup1, "Income")
                set1.color = resources.getColor(R.color.colorPrimary)
                set1.valueTextSize = 11f
                val set2 = BarDataSet(entriesGroup2, "Expenses")
                set2.color = resources.getColor(R.color.colorPrimaryDark)
                set2.valueTextSize = 11f

                val groupSpace = 0.1f
                val barSpace = 0.02f
                val barWidth = 0.45f

                val data = BarData(set1, set2)
                data.barWidth = barWidth
                data.setValueFormatter(AmountValueFormatter())

                var overviewChart = root.findViewById<BarChart>(R.id.overview_chart)
                overviewChart.data = data
                overviewChart.groupBars(currentMonth - 1.5f, groupSpace, barSpace)
                overviewChart.description.isEnabled = false

                overviewChart.xAxis.granularity = 1f
                overviewChart.xAxis.setCenterAxisLabels(true)
                overviewChart.xAxis.valueFormatter = BarChartValueFormatter()

                overviewChart.invalidate()
            }
        }
    }
}
