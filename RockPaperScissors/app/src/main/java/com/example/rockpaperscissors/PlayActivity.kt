package com.example.rockpaperscissors

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_play.*


const val SHOW_HISTORY_REQUEST_CODE = 17

class PlayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        setSupportActionBar(toolbar)
        initViews()
    }

    private fun initViews() {
        val clickListener = View.OnClickListener {view ->
            var playerChoice = 0
            val computerChoice = (0..2).random()
            val rockPaperScissors = arrayListOf<Int>(R.drawable.rock, R.drawable.paper, R.drawable.scissors)

            imageViewComputer.setImageResource(rockPaperScissors[computerChoice])

            when (view.id) {
                R.id.imageViewRock -> {
                    imageViewYou.setImageResource(R.drawable.rock)
                }
                R.id.imageViewPaper -> {
                    playerChoice = 1
                    imageViewYou.setImageResource(R.drawable.paper)
                }
                R.id.imageViewScissor -> {
                    playerChoice = 2
                    imageViewYou.setImageResource(R.drawable.scissors)
                }
                else -> {}
            }

            if (playerChoice == 0 && computerChoice == 2 || playerChoice == 1 && computerChoice == 0 || playerChoice == 2 && computerChoice == 1) {
                textViewPlayWinMsg.text = getString(R.string.you_win)
            } else if (playerChoice == computerChoice) {
                textViewPlayWinMsg.text = getString(R.string.you_draw)
            } else {
                textViewPlayWinMsg.text = getString(R.string.you_lose)
            }
        }

        imageViewRock.setOnClickListener(clickListener)
        imageViewPaper.setOnClickListener(clickListener)
        imageViewScissor.setOnClickListener(clickListener)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_play, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menuShowHistoryBtn -> {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivityForResult(intent, SHOW_HISTORY_REQUEST_CODE)
            true
        }
        else ->
            super.onOptionsItemSelected(item)
    }
}
