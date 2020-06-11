package com.example.incomeandexpensesapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.incomeandexpensesapp.ui.ViewTransactionActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

const val SHOW_EDIT_INCOME_REQUEST_CODE = 15

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_overview, R.id.navigation_income, R.id.navigation_expenses))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.navigation_edit -> {
            val intent = Intent(this, ViewTransactionActivity::class.java)

            if (findNavController(R.id.nav_host_fragment).currentDestination?.id == R.id.navigation_income) {
                intent.putExtra("transaction_type", "income")
            } else {
                intent.putExtra("transaction_type", "expenses")
            }
            startActivityForResult(intent,
                SHOW_EDIT_INCOME_REQUEST_CODE
            )

            true
        }
        else ->
            super.onOptionsItemSelected(item)
    }
}
