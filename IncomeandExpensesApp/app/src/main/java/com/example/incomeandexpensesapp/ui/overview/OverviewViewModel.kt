package com.example.incomeandexpensesapp.ui.overview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.incomeandexpensesapp.database.TransactionRepository

class OverviewViewModel(application: Application) : AndroidViewModel(application) {

    val transactionRepository = TransactionRepository(application.applicationContext)

}