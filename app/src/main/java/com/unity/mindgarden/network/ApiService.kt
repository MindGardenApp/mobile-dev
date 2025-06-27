package com.unity.mindgarden.network

import com.unity.mindgarden.model.ContentRequest
import com.unity.mindgarden.model.CurhatRequest
import com.unity.mindgarden.model.CurhatResponse
import com.unity.mindgarden.model.PredictionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/predict")
    fun predictMood(@Body content: ContentRequest): Call<PredictionResponse>

    @POST("/curhat")
    fun kirimCurhat(@Body request: CurhatRequest): Call<CurhatResponse>
}