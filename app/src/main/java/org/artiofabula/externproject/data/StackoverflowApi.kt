package org.artiofabula.externproject.data

import okhttp3.OkHttpClient
import org.artiofabula.externproject.data.answers.GetAnswersResponse
import org.artiofabula.externproject.data.questions.GetQuestionsResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection

interface StackOverflowApi {
    companion object {
        private val client: OkHttpClient by lazy {
            OkHttpClient
                .Builder()
                .hostnameVerifier { _, _ -> HttpsURLConnection.getDefaultHostnameVerifier(); true }
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS).build()
        }

        private val api: StackOverflowApi by lazy {
            Retrofit.Builder()
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl("https://api.stackexchange.com")
                .build()
                .create(StackOverflowApi::class.java)
        }

        operator fun invoke(): StackOverflowApi {
            return api
        }
    }

    @GET("2.3/questions")
    suspend fun getQuestions(@QueryMap params: Map<String, String>): Response<GetQuestionsResponse>

    @GET("2.3/questions/{id}/answers")
    suspend fun getAnswers(@Path("id") id: String, @QueryMap params: Map<String, String>): Response<GetAnswersResponse>
}