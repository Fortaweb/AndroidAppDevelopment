package com.example.incomeandexpensesapp.ui;

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.incomeandexpensesapp.R
import com.example.incomeandexpensesapp.model.Transaction
import com.example.incomeandexpensesapp.ui.TransactionAdapter
import kotlinx.android.synthetic.main.activity_add_transaction.toolbar
import kotlinx.android.synthetic.main.activity_overview_transaction.*

const val ADD_TRANSACTION_REQUEST_CODE = 15
const val TAG = "ViewTransactionActivity"

class ViewTransactionActivity : AppCompatActivity() {

    private val transactions = arrayListOf<Transaction>()
    private val transactionAdapter = TransactionAdapter(transactions)
    private val viewModel: ViewTransactionActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview_transaction)
        setSupportActionBar(toolbar)

        initViews()
        observeViewModel()
    }

    private fun initViews() {
        var currentTitle = getString(R.string.title_income_overview)

        // Retrieve which transaction is being opened
        var transactionType = intent.getStringExtra("transaction_type")
        if (transactionType == "expenses") {
            viewModel.transactions = viewModel.transactionRepository.getExpensesTransactions()
            currentTitle = getString(R.string.title_expenses_overview)
        }

        // Setup action bar with title and back button
        val actionbar = supportActionBar
        actionbar!!.title = currentTitle
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)

        // Initialize the recycler view with a linear layout manager, adapter
        incomeList.layoutManager =
            LinearLayoutManager(this@ViewTransactionActivity, RecyclerView.VERTICAL, false)
        incomeList.adapter = transactionAdapter
        incomeList.addItemDecoration(
            DividerItemDecoration(
                this@ViewTransactionActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        createItemTouchHelper().attachToRecyclerView(incomeList)

        fabAddIncome.setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
            intent.putExtra("transaction_type", transactionType)
            startActivityForResult(intent, ADD_TRANSACTION_REQUEST_CODE)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun observeViewModel() {
        viewModel.transactions.observe(this, Observer { transactions ->
            this@ViewTransactionActivity.transactions.clear()
            this@ViewTransactionActivity.transactions.addAll(transactions)
            transactionAdapter.notifyDataSetChanged()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_TRANSACTION_REQUEST_CODE -> {
                    data?.let { safeData ->
                        val transaction = safeData.getParcelableExtra<Transaction>(EXTRA_TRANSACTION)
                        transaction?.let { safeTransaction ->
                            viewModel.insertTransaction(safeTransaction)
                        } ?: run {
                            Log.e(TAG, "transaction is null")
                        }
                    } ?: run {
                        Log.e(TAG, "empty intent data received")
                    }
                }
            }
        }
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val transactionToDelete = transactions[position]

                viewModel.deleteTransaction(transactionToDelete)
            }
        }
        return ItemTouchHelper(callback)
    }

}
