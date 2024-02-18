package org.artiofabula.externproject.ui.questions

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.artiofabula.externproject.data.Dependencies
import org.artiofabula.externproject.data.questions.QuestionItem
import org.artiofabula.externproject.data.questions.QuestionsRepository
import org.artiofabula.externproject.data.questions.database.toQuestionDbEntity
import org.artiofabula.externproject.data.questions.database.toQuestionItem

class QuestionsViewModel(private val repository: QuestionsRepository) : ViewModel() {
    private lateinit var fetchingJob: Job

    private val _questions = MutableLiveData<List<QuestionItem>>()
    val questions: LiveData<List<QuestionItem>> get() = _questions

    var recyclerViewState: Parcelable? = null

    fun lazyFetchQuestions() {
        fetchingJob = CoroutineScope(Dispatchers.IO).launch {
            if (_questions.value.orEmpty().isNotEmpty()) {
                return@launch
            }

            forceFetchQuestions()
        }
    }

    private suspend fun forceFetchQuestions() {
        val topQuestions = runCatching {
            val questionsFromWeb = repository.getTopQuestions()

            CoroutineScope(Dispatchers.IO).launch { // fire and forget; faster getting result
                Dependencies.questionsDao.replaceAllQuestionsTo(questionsFromWeb.map { it.toQuestionDbEntity() })
            }

            questionsFromWeb
        }.getOrElse {
            Dependencies.questionsDao.getAllQuestions().map { it.toQuestionItem() }
        }

        withContext(Dispatchers.Main) { _questions.value = topQuestions }
    }

    override fun onCleared() {
        super.onCleared()

        if (this::fetchingJob.isInitialized) {
            fetchingJob.cancel()
        }
    }
}