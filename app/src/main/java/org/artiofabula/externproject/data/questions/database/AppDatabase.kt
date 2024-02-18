package org.artiofabula.externproject.data.questions.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [QuestionDbEntity::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun getQuestionsDao(): QuestionsDao
}