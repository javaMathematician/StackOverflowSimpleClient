package org.artiofabula.externproject.ui.answers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.artiofabula.externproject.R
import org.artiofabula.externproject.data.answers.AnswerItem
import org.artiofabula.externproject.databinding.RecyclerViewAnswerBinding

class AnswersAdapter(private val answers: List<AnswerItem>) : RecyclerView.Adapter<AnswersAdapter.AnswerViewHolder>() {
    inner class AnswerViewHolder(val binding: RecyclerViewAnswerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        return AnswerViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_view_answer, parent, false))
    }

    override fun getItemCount(): Int {
        return answers.size
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        holder.binding.answer = answers[position]
    }
}