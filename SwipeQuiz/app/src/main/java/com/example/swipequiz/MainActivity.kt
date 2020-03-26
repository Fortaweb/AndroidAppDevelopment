package com.example.swipequiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val questionAdapter = QuestionAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        // Insert the questions into the list
        for (i in Question.QUESTIONS.indices) {
            questionAdapter.addItem(Question(Question.QUESTIONS[i], Question.ANSWERS[i]))
        }

        // Create list
        questionsList.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = questionAdapter
            addItemDecoration(DividerItemDecoration(questionsList.context, DividerItemDecoration.VERTICAL))
        }

        // Make items swipable
        val itemTouchHelperCallback =
            // Only allow LEFT and RIGHT movements
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    viewHolder2: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                // Check on swiped if answer is correct or wrong
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDirection: Int) {
                    // Remove item from list if user swiped correctly
                    if (swipeDirection == ItemTouchHelper.RIGHT && questionAdapter.getItem(viewHolder.adapterPosition).answer
                            || swipeDirection == ItemTouchHelper.LEFT && !questionAdapter.getItem(viewHolder.adapterPosition).answer) {
                        questionAdapter.removeItem(viewHolder.adapterPosition)
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), getString(R.string.answer_not_correct), Snackbar.LENGTH_SHORT).show()
                    }

                    // Update list to get items back that have been swiped incorrectly
                    questionAdapter.notifyDataSetChanged()

                    // If all questions are done, show message
                    if (questionAdapter.itemCount == 0) {
                        Snackbar.make(findViewById(android.R.id.content), getString(R.string.finished), Snackbar.LENGTH_INDEFINITE).show()
                    }
                }
            }

        // Attach swipe event to question list
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(questionsList)
    }
}