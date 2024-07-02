package com.example.upstoxpoc.data.network

import com.example.upstoxpoc.data.model.CryptoItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("coin")
    suspend fun getCryptoCoins(): Response<List<CryptoItem>>


}