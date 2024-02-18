package org.artiofabula.externproject.data.questions

import org.artiofabula.externproject.data.StackOverflowApi

class QuestionsRepository(private val api: StackOverflowApi) {
    suspend fun getTopQuestions(): List<QuestionItem> {
        val response = api.getQuestions(
            mapOf(
                "order" to "desc", "sort" to "creation", "tagged" to "android", "pagesize" to "100", "site" to "stackoverflow", "filter" to "!amoFX_(nvKZgsX"
            )
        )
        val body = response.body() ?: throw IllegalStateException("Something went bad, while calling API")
        return body.questionItems
    }
}