package com.example.swipequiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_question.view.*

class QuestionAdapter():
    RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    private var questions = ArrayList<Question>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    fun addItem(question: Question) {
        questions.add(question)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): Question {
        return questions.get(position)
    }

    fun removeItem(position: Int) {
        questions.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(question : Question) {
            // Change item text to the question
            itemView.textViewQuestion.text = question.question

            // Add click listener, if user clicks instead of swipes, show correct answer
            itemView.setOnClickListener {
                val msg = if(question.answer) itemView.context.getString(R.string.answer_is_true) else itemView.context.getString(R.string.answer_is_false)
                Snackbar.make(itemView, msg, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}