package org.artiofabula.externproject.ui.answers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.artiofabula.externproject.data.answers.AnswerItem
import org.artiofabula.externproject.data.answers.AnswersRepository

class AnswersViewModel(private val repository: AnswersRepository) : ViewModel() {
    private lateinit var fetchingJob: Job

    private val _answers = MutableLiveData<List<AnswerItem>>()
    val answers: LiveData<List<AnswerItem>> get() = _answers

    fun fetchAnswersFromWeb(questionId: Long): Job {
        fetchingJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) { // retries
                val result = runCatching { repository.getAnswersForQuestion(questionId) }
                if (result.isFailure) {
                    continue
                }

                withContext(Dispatchers.Main) { _answers.value = result.getOrNull()?.questionItems }
                break
            }
        }

        return fetchingJob
    }

    override fun onCleared() {
        super.onCleared()

        if (this::fetchingJob.isInitialized) {
            fetchingJob.cancel()
        }
    }
}