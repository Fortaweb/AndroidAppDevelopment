package com.example.incomeandexpensesapp.ui.overview

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.incomeandexpensesapp.R

class OverviewFragment : Fragment() {

    private lateinit var overviewViewModel: OverviewViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        overviewViewModel =
                ViewModelProviders.of(this).get(OverviewViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_overview, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        overviewViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
