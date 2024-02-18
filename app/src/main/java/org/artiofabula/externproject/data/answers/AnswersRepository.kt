package org.artiofabula.externproject.data.answers

import org.artiofabula.externproject.data.StackOverflowApi

class AnswersRepository(private val api: StackOverflowApi) {
    suspend fun getAnswersForQuestion(questionId: Long): GetAnswersResponse {
        val response = api.getAnswers(
            questionId.toString(), mapOf("site" to "stackoverflow", "filter" to "!WWsh2-5LBtfz3iQjzv*iVb*lGN4BRnmvsBFux)y")
        )

        return response.body() ?: throw IllegalStateException("Something went bad, while calling API")
    }
}