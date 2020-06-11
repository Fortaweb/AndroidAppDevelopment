package com.example.incomeandexpensesapp.ui;

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.incomeandexpensesapp.database.TransactionRepository
import com.example.incomeandexpensesapp.model.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewTransactionActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    val transactionRepository = TransactionRepository(application.applicationContext)

    var transactions: LiveData<List<Transaction>> = transactionRepository.getIncomeTransactions()

    fun insertTransaction(transaction: Transaction) {
        ioScope.launch {
            transactionRepository.insertTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        ioScope.launch {
            transactionRepository.deleteTransaction(transaction)
        }
    }

}
