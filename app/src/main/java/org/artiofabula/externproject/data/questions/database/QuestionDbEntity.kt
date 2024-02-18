package org.artiofabula.externproject.data.questions.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.artiofabula.externproject.data.answers.Owner
import org.artiofabula.externproject.data.questions.QuestionItem

@Entity(tableName = "questions")
data class QuestionDbEntity(
    @PrimaryKey val id: Long,
    val ownerDisplayName: String,
    val body: String,
    val title: String,
    val answerCount: Int,
)

fun QuestionDbEntity.toQuestionItem(): QuestionItem {
    return QuestionItem(
        questionId = this.id,
        owner = Owner(this.ownerDisplayName),
        title = this.title,
        body = this.body,
        answerCount = this.answerCount
    )
}

fun QuestionItem.toQuestionDbEntity(): QuestionDbEntity {
    return QuestionDbEntity(
        id = this.questionId,
        ownerDisplayName = this.owner.displayName,
        body = this.body,
        title = this.title,
        answerCount = this.answerCount
    )
}