package com.example.upstoxpoc.domain.repository

import com.example.upstoxpoc.data.model.CryptoItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CryptoCoinRepository {
    suspend fun getCryptoCoins(): Flow<Response<List<CryptoItem>>>
}