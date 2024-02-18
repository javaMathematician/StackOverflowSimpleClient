package org.artiofabula.externproject.ui.answers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.artiofabula.externproject.data.answers.AnswersRepository
import org.artiofabula.externproject.data.questions.QuestionsRepository

class AnswersViewModelFactory(
    private val repository: AnswersRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AnswersViewModel(repository) as T
    }
}