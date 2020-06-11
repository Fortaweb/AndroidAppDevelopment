package com.example.incomeandexpensesapp.ui.income

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.incomeandexpensesapp.R
import com.example.incomeandexpensesapp.model.PieChartEntry
import com.example.incomeandexpensesapp.ui.AmountValueFormatter
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_income.*
import java.util.*
import kotlin.collections.ArrayList


class IncomeFragment : Fragment() {

    private val pieChartEntries = arrayListOf<PieChartEntry>()
    private val viewModel: IncomeViewModel by viewModels()
    private lateinit var incomeViewModel: IncomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        incomeViewModel =
                ViewModelProviders.of(this).get(IncomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_income, container, false)

        observeViewModel()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        selectIncomeMonth.post(Runnable {
            val cal: Calendar = Calendar.getInstance()
            selectIncomeMonth.setSelection(cal.get(Calendar.MONTH))
        })

        selectIncomeMonth?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.transactions = viewModel.transactionRepository.getIncomePieChartEntries(String.format("%02d", position + 1))
                observeViewModel()
            }
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun observeViewModel() {
        viewModel.transactions.observe(viewLifecycleOwner, Observer { transactions ->
            this@IncomeFragment.pieChartEntries.clear()
            this@IncomeFragment.pieChartEntries.addAll(transactions)

            val listPie = ArrayList<PieEntry>()
            val listColors = ArrayList<Int>()

            for (p in this@IncomeFragment.pieChartEntries) {
                listPie.add(PieEntry(p.total, p.category))
            }

            for (c in ColorTemplate.VORDIPLOM_COLORS) {
                listColors.add(c)
            }

            val pieDataSet = PieDataSet(listPie, "")
            pieDataSet.colors = listColors

            val pieData = PieData(pieDataSet)
            pieData.setValueFormatter(AmountValueFormatter())
            pieData.setValueTextSize(15f)

            val pieChart = this@IncomeFragment.expenses_chart
            pieChart.data = pieData

            pieChart.isDrawHoleEnabled = false
            pieChart.description.isEnabled = false
            pieChart.setEntryLabelColor(R.color.colorAccent)
            pieChart.isRotationEnabled = false
            pieChart.animateY(800, Easing.EaseInOutQuad)
        })
    }
}
