package org.artiofabula.externproject.data.answers

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class GetAnswersResponse(@JsonProperty("items") val questionItems: List<AnswerItem>) : Serializable

data class AnswerItem(
    @JsonProperty("body") val body: String,
    @JsonProperty("score") val score: Int,
    @JsonProperty("owner") val owner: Owner,
) : Serializable