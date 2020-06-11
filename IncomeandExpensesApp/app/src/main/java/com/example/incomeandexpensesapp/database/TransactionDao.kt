package com.example.incomeandexpensesapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.incomeandexpensesapp.model.PieChartEntry
import com.example.incomeandexpensesapp.model.Transaction

@Dao
interface TransactionDao {

    @Insert
    fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM transaction_table WHERE transaction_type = 'income' ORDER BY id DESC")
    fun getIncomeTransactions(): LiveData<List<Transaction>>

    @Query("SELECT SUM(amount) AS total, category FROM transaction_table WHERE transaction_type = 'income' AND strftime('%m', datetime(date/1000, 'unixepoch')) = :month GROUP BY category")
    fun getIncomePieChartEntries(month : String): LiveData<List<PieChartEntry>>

    @Query("SELECT SUM(amount) FROM transaction_table WHERE transaction_type = 'income' AND strftime('%m', datetime(date/1000, 'unixepoch')) = :month")
    fun getTotalAmountIncomeForMonth(month : String): Float

    @Query("SELECT * FROM transaction_table WHERE transaction_type = 'expenses' ORDER BY id DESC")
    fun getExpensesTransactions(): LiveData<List<Transaction>>

    @Query("SELECT SUM(amount) AS total, category FROM transaction_table WHERE transaction_type = 'expenses' AND strftime('%m', datetime(date/1000, 'unixepoch')) = :month GROUP BY category")
    fun getExpensesPieChartEntries(month : String): LiveData<List<PieChartEntry>>

    @Query("SELECT SUM(amount) FROM transaction_table WHERE transaction_type = 'expenses' AND strftime('%m', datetime(date/1000, 'unixepoch')) = :month")
    fun getTotalAmountExpensesForMonth(month : String): Float

    @Update
    fun updateTransaction(transaction: Transaction)

    @Delete
    fun deleteTransaction(transaction: Transaction)

}