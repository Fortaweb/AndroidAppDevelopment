package com.example.incomeandexpensesapp.ui;

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.incomeandexpensesapp.R
import com.example.incomeandexpensesapp.model.Transaction
import kotlinx.android.synthetic.main.activity_add_transaction.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.text.ParseException
import java.text.SimpleDateFormat

const val EXTRA_TRANSACTION = "EXTRA_TRANSACTION"

class AddTransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)
        setSupportActionBar(toolbar)

        initViews()
    }

    private fun initViews() {
        // Setup action bar with title and back button
        val actionbar = supportActionBar

        var transactionType = intent.getStringExtra("transaction_type")
        if (transactionType == "expenses") {
            actionbar!!.title = getString(R.string.title_add_expenses)

            var adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.expenses_categories, R.layout.support_simple_spinner_dropdown_item)
            inputCategory.adapter = adapter
        } else {
            actionbar!!.title = getString(R.string.title_add_income)

            var adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.income_categories, R.layout.support_simple_spinner_dropdown_item)
            inputCategory.adapter = adapter
        }

        actionbar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)

        saveIncomeBtn.setOnClickListener { onSaveClick() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun onSaveClick() {
        var transactionType = intent.getStringExtra("transaction_type")

        when {
            inputName.text.toString().isBlank() -> {
                Toast.makeText(this,"Please enter the name of your $transactionType entry", Toast.LENGTH_SHORT).show()
            }
            inputAmount.text.toString().isBlank() -> {
                Toast.makeText(this,"Please enter the amount of your $transactionType entry", Toast.LENGTH_SHORT).show()
            }
            inputAmount.text.toString().toDoubleOrNull() == null -> {
                Toast.makeText(this,"The amount you've entered is not valid, please enter a numeric value", Toast.LENGTH_SHORT).show()
            }
            inputDate.text.toString().isBlank() -> {
                Toast.makeText(this,"Please enter the date of your $transactionType entry", Toast.LENGTH_SHORT).show()
            }
            !isValidDate(inputDate.text.toString()) -> {
                Toast.makeText(this,"The date you've entered is not valid, it should be dd-mm-yyyy", Toast.LENGTH_SHORT).show()
            }
            else -> {
                val transaction = Transaction(
                    transactionType,
                    inputName.text.toString(),
                    inputAmount.text.toString().toDouble(),
                    SimpleDateFormat("dd-MM-yyyy").parse(inputDate.text.toString()),
                    inputCategory.selectedItem.toString()
                )
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_TRANSACTION, transaction)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    private fun isValidDate(inputDate: String): Boolean {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")

        try {
            dateFormat.parse(inputDate)
        } catch (pe: ParseException) {
            return false
        }

        return true
    }

}
