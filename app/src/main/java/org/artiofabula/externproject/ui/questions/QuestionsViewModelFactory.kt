package org.artiofabula.externproject.ui.questions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.artiofabula.externproject.data.questions.QuestionsRepository

class QuestionsViewModelFactory(
    private val repository: QuestionsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuestionsViewModel(repository,) as T
    }
}