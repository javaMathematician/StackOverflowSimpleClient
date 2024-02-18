package org.artiofabula.externproject.data.questions.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface QuestionsDao {
    @Insert(entity = QuestionDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertNewQuestion(questionDbEntity: QuestionDbEntity)

    @Query("SELECT * FROM questions")
    fun getAllQuestions(): List<QuestionDbEntity>

    @Query("DELETE FROM questions")
    fun deleteAllQuestions()

    @Transaction
    fun replaceAllQuestionsTo(questionDbEntities: List<QuestionDbEntity>) {
        deleteAllQuestions()
        questionDbEntities.forEach { insertNewQuestion(it) }
    }
}
