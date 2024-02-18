package org.artiofabula.externproject.ui.answers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.artiofabula.externproject.data.StackOverflowApi
import org.artiofabula.externproject.data.answers.AnswersRepository
import org.artiofabula.externproject.data.questions.QuestionItem
import org.artiofabula.externproject.databinding.FragmentAnswersBinding

class AnswersFragment : Fragment() {
    private var _binding: FragmentAnswersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AnswersViewModel by viewModels {
        val api = StackOverflowApi()
        val repository = AnswersRepository(api)
        val questionsViewModelFactory = AnswersViewModelFactory(repository)
        questionsViewModelFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAnswersBinding.inflate(inflater, container, false)

        val serializable = arguments?.getSerializable("question_data")
        val question = serializable as? QuestionItem ?: throw IllegalStateException("Got strange stuff: $serializable")

        val fetchingJob = viewModel.fetchAnswersFromWeb(question.questionId)
        CoroutineScope(Dispatchers.Unconfined).launch {
            delay(2_000)

            if (!fetchingJob.isActive) {
                return@launch
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Loading is taking too long... Maybe there is no internet?", Toast.LENGTH_LONG).show()
            }
        }

        binding.answersLoadingProgressBar.visibility = View.VISIBLE
        binding.question = question
        viewModel.answers.observe(viewLifecycleOwner) { answers ->
            binding.recyclerViewAnswers.apply {
                binding.answersLoadingProgressBar.visibility = View.INVISIBLE
                this.layoutManager = LinearLayoutManager(requireContext())
                this.adapter = AnswersAdapter(answers)
                this.setHasFixedSize(true)
            }
        }

        return binding.root
    }
}