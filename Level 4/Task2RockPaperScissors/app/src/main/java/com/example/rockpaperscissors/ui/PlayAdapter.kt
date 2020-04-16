package com.example.rockpaperscissors.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.model.Play
import kotlinx.android.synthetic.main.item_history.view.*

class PlayAdapter() : RecyclerView.Adapter<PlayAdapter.ViewHolder>() {

    companion object {
        var playHistoryList = arrayListOf<Play>()
    }

    val rockPaperScissors = arrayListOf(
        R.drawable.rock,
        R.drawable.paper,
        R.drawable.scissors
    )
    val historyTitles = arrayListOf(
        R.string.you_win,
        R.string.you_draw,
        R.string.you_lose
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        )
    }

    fun setHistory(playHistory : List<Play>) {
        playHistoryList.clear()
        playHistoryList.addAll(playHistory)
    }

    override fun getItemCount(): Int = playHistoryList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(playHistoryList[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(play: Play) {
            itemView.historyTitle.text = itemView.context.getString(historyTitles[play.result])
            itemView.historyDate.text = play.date.toString()
            itemView.imageViewHistoryYou.setImageResource(rockPaperScissors[play.playerChoice])
            itemView.imageViewHistoryComputer.setImageResource(rockPaperScissors[play.computerChoice])
        }
    }
}