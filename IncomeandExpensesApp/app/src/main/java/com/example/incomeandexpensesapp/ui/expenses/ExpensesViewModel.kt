package com.example.incomeandexpensesapp.ui.expenses

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.incomeandexpensesapp.database.TransactionRepository
import com.example.incomeandexpensesapp.model.PieChartEntry

class ExpensesViewModel(application: Application) : AndroidViewModel(application) {

    val transactionRepository = TransactionRepository(application.applicationContext)

    var transactions: LiveData<List<PieChartEntry>> = transactionRepository.getIncomePieChartEntries("01")
}