package com.example.studentportal

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_portal.view.*


class PortalAdapter():
    RecyclerView.Adapter<PortalAdapter.ViewHolder>() {

    companion object {
        var portals = ArrayList<Portal>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_portal, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return portals.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(portals[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(portal : Portal) {
            // Fill in title and url of portal in the card view
            itemView.textViewTitle.text = portal.title
            itemView.textViewUrl.text = portal.url

            // Add click listener, if user clicks open URL in browser
            itemView.setOnClickListener {view ->
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(itemView.textViewUrl.text.toString()))
                startActivity(view.context, browserIntent, null)
            }
    }
}
}