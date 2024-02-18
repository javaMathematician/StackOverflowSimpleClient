package org.artiofabula.externproject.data

import android.content.Context
import androidx.room.Room
import org.artiofabula.externproject.data.questions.database.AppDatabase
import org.artiofabula.externproject.data.questions.database.QuestionsDao

object Dependencies {
    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }

    private val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "questions_database").build()
    }

    val questionsDao: QuestionsDao by lazy { appDatabase.getQuestionsDao() }
}