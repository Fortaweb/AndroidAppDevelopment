package com.example.studentportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.studentportal.PortalAdapter.Companion.portals
import kotlinx.android.synthetic.main.add_portal_activity.*

class AddPortalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_portal_activity)
        initViews()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initViews() {
        // Setup action bar with title and back button
        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.create_portal_title)
        actionbar.setDisplayHomeAsUpEnabled(true)

        // Set onclick listener for add portal button
        addPortalBtn.setOnClickListener() {
            portals.add(Portal(portalTitleInput.text.toString(), portalUrlInput.text.toString()))
            this.finish()
        }
    }
}
