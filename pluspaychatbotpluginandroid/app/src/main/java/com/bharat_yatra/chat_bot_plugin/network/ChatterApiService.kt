package com.bharat_yatra.chat_bot_plugin.network

import com.bharat_yatra.chat_bot_plugin.models.AppGuideStepsRequest
import com.bharat_yatra.chat_bot_plugin.models.AppGuideStepsResponse
import com.bharat_yatra.chat_bot_plugin.models.ChatterRequest
import com.bharat_yatra.chat_bot_plugin.models.ChatterResponse
import com.bharat_yatra.chat_bot_plugin.models.GeneralPromptRequest
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ChatterApiService {

    @POST("default/apiAI")
    @Headers("Content-Type: application/json")
    suspend fun sendMessage(@Body request: ChatterRequest): ChatterResponse

    @POST("default/pluspaysteps")
    @Headers("Content-Type: application/json")
    suspend fun plusPayGuideSteps(@Body request: AppGuideStepsRequest): AppGuideStepsResponse

    @POST("default/GeneralPrompts")
    @Headers("Content-Type: application/json")
    suspend fun plusPayGeneralPrompts(@Body request: GeneralPromptRequest): AppGuideStepsResponse

    companion object {
        private fun getRetrofit(baseUrl: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(
                    okhttp3.OkHttpClient.Builder()
                        .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                        .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                        .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                        .addInterceptor(
                            okhttp3.logging.HttpLoggingInterceptor().apply {
                                level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
                            }
                        )
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getChatterApiService(): ChatterApiService {
            return getRetrofit("https://015ev5js6b.execute-api.us-east-1.amazonaws.com/").create(
                ChatterApiService::class.java)
        }

        fun getPlusPayGuideStepsService(): ChatterApiService {
            return getRetrofit("https://btzf0gxmu7.execute-api.us-east-1.amazonaws.com/").create(
                ChatterApiService::class.java)
        }

        fun getGeneralPromptsService(): ChatterApiService {
            return getRetrofit("https://rbdl02fg72.execute-api.us-east-1.amazonaws.com/").create(
                ChatterApiService::class.java)
        }
    }
}
