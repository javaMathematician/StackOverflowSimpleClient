package org.artiofabula.externproject.ui.questions

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.artiofabula.externproject.R
import org.artiofabula.externproject.data.questions.QuestionItem
import org.artiofabula.externproject.databinding.RecyclerViewQuestionBinding

class QuestionsAdapter(private val questions: List<QuestionItem>, private val clickListener: QuestionsRecyclerViewClickListener) : RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {
    inner class QuestionViewHolder(val recyclerViewQuestionBinding: RecyclerViewQuestionBinding) : RecyclerView.ViewHolder(recyclerViewQuestionBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_view_question, parent, false))
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val binding = holder.recyclerViewQuestionBinding
        val question = questions[position]

        binding.question = question
        binding.questionCardView.setCardBackgroundColor(Color.parseColor(if (question.answerCount != 0) "#FFDFFFD7" else "#FFFFD7D7"))
        binding.questionCardView.apply {
            setOnClickListener { clickListener.onRecyclerViewItemClick(this, question = question) }
        }
    }
}