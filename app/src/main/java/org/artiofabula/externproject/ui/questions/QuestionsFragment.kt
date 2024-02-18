package org.artiofabula.externproject.ui.questions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import org.artiofabula.externproject.R
import org.artiofabula.externproject.data.StackOverflowApi
import org.artiofabula.externproject.data.questions.QuestionItem
import org.artiofabula.externproject.data.questions.QuestionsRepository
import org.artiofabula.externproject.databinding.FragmentQuestionsBinding

class QuestionsFragment : Fragment(), QuestionsRecyclerViewClickListener {
    companion object {
        const val keyRecyclerViewPosition = "KEY_RECYCLER_VIEW_POSITION"
    }

    private lateinit var navController: NavController

    private var _binding: FragmentQuestionsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: QuestionsViewModel by viewModels {
        val api = StackOverflowApi()
        val repository = QuestionsRepository(api)
        val questionsViewModelFactory = QuestionsViewModelFactory(repository)
        questionsViewModelFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        binding.questionLoadingProgressBar.visibility = View.VISIBLE

        viewModel.lazyFetchQuestions()
        viewModel.questions.observe(viewLifecycleOwner) { questions ->
            binding.recyclerViewQuestions.apply {
                binding.questionLoadingProgressBar.visibility = View.INVISIBLE

                this.layoutManager = LinearLayoutManager(requireContext()).apply {
                    val savedState = savedInstanceState?.getParcelable(keyRecyclerViewPosition) ?: viewModel.recyclerViewState
                    savedState?.let { onRestoreInstanceState(it) }
                }

                this.setHasFixedSize(true)
                this.adapter = QuestionsAdapter(questions, this@QuestionsFragment)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        _binding?.recyclerViewQuestions?.layoutManager?.onSaveInstanceState()?.let { outState.putParcelable(keyRecyclerViewPosition, it) }
    }

    override fun onRecyclerViewItemClick(view: View, question: QuestionItem) {
        val bundle = Bundle()
        bundle.putSerializable("question_data", question)

        _binding?.recyclerViewQuestions?.layoutManager?.onSaveInstanceState()?.let { viewModel.recyclerViewState = it }
        navController.navigate(R.id.action_questions_fragment_to_question_fragment, bundle)
    }
}