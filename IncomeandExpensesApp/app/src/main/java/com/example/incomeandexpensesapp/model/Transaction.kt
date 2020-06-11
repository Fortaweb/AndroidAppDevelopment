package com.example.incomeandexpensesapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "transaction_table")
data class Transaction(
    var transaction_type: String,
    var name: String,
    var amount: Double,
    var date: Date,
    var category: String,

    @PrimaryKey(autoGenerate = true)
    var id : Long? = null
) : Parcelable