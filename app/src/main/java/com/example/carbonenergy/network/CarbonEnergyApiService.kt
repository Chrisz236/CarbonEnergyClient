package com.example.carbonenergy.network

import com.example.carbonenergy.model.User
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "http://10.0.0.70:8080/"

// build HTTP client
private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
    .baseUrl(BASE_URL)
    .build()

interface CarbonEnergyApiService {
    @GET("carbon-energy/find-user/{id}")
    suspend fun getUser(@Path("id") id: String): User
}

// public api interface instance
object CarbonEnergyApi {
    val retrofitService: CarbonEnergyApiService by lazy {
        retrofit.create(CarbonEnergyApiService::class.java)
    }
}