package com.example.incomeandexpensesapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.incomeandexpensesapp.model.PieChartEntry
import com.example.incomeandexpensesapp.model.Transaction

class TransactionRepository(context: Context) {

    private val transactionDao: TransactionDao

    init {
        val database = TransactionRoomDatabase.getDatabase(context)
        transactionDao = database!!.transactionDao()
    }

    fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }

    fun getIncomeTransactions(): LiveData<List<Transaction>> {
        return transactionDao.getIncomeTransactions()
    }

    fun getIncomePieChartEntries(month : String): LiveData<List<PieChartEntry>> {
        return transactionDao.getIncomePieChartEntries(month)
    }

    fun getTotalAmountIncomeForMonth(month : String): Float {
        return transactionDao.getTotalAmountIncomeForMonth(month)
    }

    fun getExpensesTransactions(): LiveData<List<Transaction>> {
        return transactionDao.getExpensesTransactions()
    }

    fun getExpensesPieChartEntries(month : String): LiveData<List<PieChartEntry>> {
        return transactionDao.getExpensesPieChartEntries(month)
    }

    fun getTotalAmountExpensesForMonth(month : String): Float {
        return transactionDao.getTotalAmountExpensesForMonth(month)
    }

    fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction)
    }

    fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction)
    }

}