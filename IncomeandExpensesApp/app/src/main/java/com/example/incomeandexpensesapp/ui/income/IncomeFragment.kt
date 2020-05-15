package com.example.incomeandexpensesapp.ui.income

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.incomeandexpensesapp.R

class IncomeFragment : Fragment() {

    private lateinit var incomeViewModel: IncomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        incomeViewModel =
                ViewModelProviders.of(this).get(IncomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_income, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        incomeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
