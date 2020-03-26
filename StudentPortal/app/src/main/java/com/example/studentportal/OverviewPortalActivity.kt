package com.example.studentportal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.overview_portal_activity.*

const val ADD_PORTAL_REQUEST_CODE = 15

class OverviewPortalActivity : AppCompatActivity() {

    private val portalAdapter = PortalAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.overview_portal_activity)

        initViews()
    }

    private fun initViews() {
        // Open add portal activity
        addPortalFab.setOnClickListener {
            val intent = Intent(this, AddPortalActivity::class.java)
            startActivityForResult(intent, ADD_PORTAL_REQUEST_CODE)
        }

        // Create list
        portalList.apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = portalAdapter
        }
    }

    override fun onResume() {
        super.onResume()

        // Refresh portal overview
        portalAdapter.notifyDataSetChanged()
    }
}
