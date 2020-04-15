package com.example.rockpaperscissors.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.database.PlayRepository
import com.example.rockpaperscissors.model.Play
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.fragment_play_result.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


const val SHOW_HISTORY_REQUEST_CODE = 17

class PlayActivity : AppCompatActivity() {

    private lateinit var playRepository: PlayRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        setSupportActionBar(toolbar)
        playRepository = PlayRepository(this)
        initViews()
    }

    private fun initViews() {
        val clickListener = View.OnClickListener {view ->
            var playerChoice = 0 // Player choice (0 -> rock, 1 -> paper or 2 -> scissors)
            val computerChoice = (0..2).random() // Random computer choice (0 -> rock, 1 -> paper or 2 -> scissors)
            var result = 0;
            val rockPaperScissors = arrayListOf(
                R.drawable.rock,
                R.drawable.paper,
                R.drawable.scissors
            )

            // Update computer result image
            imageViewComputer.setImageResource(rockPaperScissors[computerChoice])

            // Set correct chosen image for the player
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

            // Check who wins (rock beats scissors, paper beats rock and scissors beat paper)
            if (playerChoice == 0 && computerChoice == 2 || playerChoice == 1 && computerChoice == 0 || playerChoice == 2 && computerChoice == 1) {
                textViewPlayWinMsg.text = getString(R.string.you_win)
            } else if (playerChoice == computerChoice) {
                textViewPlayWinMsg.text = getString(R.string.you_draw)
                result = 1
            } else {
                textViewPlayWinMsg.text = getString(R.string.you_lose)
                result = 2
            }

            // Add play result to database
            addPlay(playerChoice, computerChoice, result)

            // Make sure the result block is visible
            playResultInclude.visibility = View.VISIBLE
        }

        // Add listeners for bottom 3 images (Rock, Paper, Scissors)
        imageViewRock.setOnClickListener(clickListener)
        imageViewPaper.setOnClickListener(clickListener)
        imageViewScissor.setOnClickListener(clickListener)

        // Show current statistics from previous games
        getStatistics()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_play, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menuShowHistoryBtn -> {
            // Open history activity
            val intent = Intent(this, HistoryActivity::class.java)
            startActivityForResult(intent,
                SHOW_HISTORY_REQUEST_CODE
            )
            true
        }
        else ->
            super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        // Update statistics on back to this activity
        getStatistics()
    }

    private fun addPlay(playerChoice : Int, computerChoice : Int, result : Int) {
        mainScope.launch {
            // Create new Play object with the current date, player and computer choice and the result (0 -> win, 1 -> draw or 2 -> lose)
            val play = Play(
                date = Date(),
                playerChoice = playerChoice,
                computerChoice = computerChoice,
                result = result
            )

            withContext(Dispatchers.IO) {
                playRepository.insertPlay(play)
                getStatistics()
            }
        }
    }

    private fun getStatistics() {
        mainScope.launch {
            val playStatistics = withContext(Dispatchers.IO) {
                playRepository.getStatistics()
            }

            // Count how much wins, draw and loses there are
            var win = 0
            var draw = 0
            var lose = 0
            for (stat in playStatistics) {
                if (stat.result == 0) { win = stat.total }
                if (stat.result == 1) { draw = stat.total }
                if (stat.result == 2) { lose = stat.total }
            }

            // Hide last result if there is no history (history has been deleted)
            if (win == 0 && draw == 0 && lose == 0) {
                playResultInclude.visibility = View.INVISIBLE
            }

            // Update statistic text
            textViewStatisticsValue.text = getString(R.string.play_statistics_values, win, draw, lose)
        }
    }

}
