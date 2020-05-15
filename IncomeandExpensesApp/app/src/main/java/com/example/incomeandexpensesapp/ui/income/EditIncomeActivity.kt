package com.example.incomeandexpensesapp.ui.income;

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.incomeandexpensesapp.R
import kotlinx.android.synthetic.main.activity_edit_income.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class EditIncomeActivity : AppCompatActivity() {

    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_income)
        setSupportActionBar(toolbar)
        initViews()
    }

    private fun initViews() {
        // Setup action bar with title and back button
        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.title_edit_income)
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_history, menu)
        return true
    }*/

}
