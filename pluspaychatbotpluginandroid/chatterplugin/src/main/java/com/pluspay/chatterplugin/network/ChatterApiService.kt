package com.pluspay.chatterplugin.network

import com.pluspay.chatterplugin.models.AppGuideStepsRequest
import com.pluspay.chatterplugin.models.AppGuideStepsResponse
import com.pluspay.chatterplugin.models.ChatterRequest
import com.pluspay.chatterplugin.models.ChatterResponse
import com.pluspay.chatterplugin.models.GeneralPromptRequest
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
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getChatterApiService(): ChatterApiService {
            return getRetrofit("https://015ev5js6b.execute-api.us-east-1.amazonaws.com/").create(ChatterApiService::class.java)
        }

        fun getPlusPayGuideStepsService(): ChatterApiService {
            return getRetrofit("https://btzf0gxmu7.execute-api.us-east-1.amazonaws.com/").create(ChatterApiService::class.java)
        }

        fun getGeneralPromptsService(): ChatterApiService {
            return getRetrofit("https://rbdl02fg72.execute-api.us-east-1.amazonaws.com/").create(ChatterApiService::class.java)
        }
    }
}
