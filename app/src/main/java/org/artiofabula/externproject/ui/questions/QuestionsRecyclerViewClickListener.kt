package org.artiofabula.externproject.ui.questions

import android.view.View
import org.artiofabula.externproject.data.questions.QuestionItem

interface QuestionsRecyclerViewClickListener {
    fun onRecyclerViewItemClick(view: View, question: QuestionItem)
}
