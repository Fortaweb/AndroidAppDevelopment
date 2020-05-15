package com.example.incomeandexpensesapp.ui.expenses

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.incomeandexpensesapp.R

class ExpensesFragment : Fragment() {

    private lateinit var expensesViewModel: ExpensesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        expensesViewModel =
                ViewModelProviders.of(this).get(ExpensesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_expenses, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        expensesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
