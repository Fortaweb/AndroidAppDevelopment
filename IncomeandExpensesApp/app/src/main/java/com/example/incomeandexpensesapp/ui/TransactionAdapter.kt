package com.example.incomeandexpensesapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.incomeandexpensesapp.R
import com.example.incomeandexpensesapp.model.Transaction
import kotlinx.android.synthetic.main.item_transaction.view.*
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter(private val reminders: List<Transaction>) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(transaction: Transaction) {
            var dateFormat = SimpleDateFormat("dd-MM-yyyy")
            val otherSymbols = DecimalFormatSymbols(Locale.getDefault())
            otherSymbols.decimalSeparator = ','
            otherSymbols.groupingSeparator = '.'
            val amountFormat = DecimalFormat("#,###,##0.00", otherSymbols)

            itemView.transactionName.text = transaction.name
            itemView.transactionCategory.text = transaction.category
            itemView.transactionDate.text = dateFormat.format(transaction.date)
            itemView.transactionAmount.text = "€ " + amountFormat.format(transaction.amount.toString().toDouble())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return reminders.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reminders[position])
    }


}