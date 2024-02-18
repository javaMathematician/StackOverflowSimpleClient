package org.artiofabula.externproject.data.questions

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.artiofabula.externproject.data.answers.Owner
import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class QuestionItem(
    @JsonProperty("question_id") val questionId: Long,
    @JsonProperty("owner") val owner: Owner,
    @JsonProperty("title") val title: String,
    @JsonProperty("body") val body: String,
    @JsonProperty("answer_count") val answerCount: Int,
) : Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class GetQuestionsResponse(@JsonProperty("items") val questionItems: List<QuestionItem>) : Serializable