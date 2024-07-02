package com.example.upstoxpoc.domain.repository

import com.example.upstoxpoc.data.model.CryptoItem
import com.example.upstoxpoc.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class CryptoCoinRepositoryImpl @Inject constructor(private val apiService: ApiService) : CryptoCoinRepository {
    override suspend fun getCryptoCoins(): Flow<Response<List<CryptoItem>>> = flow{
        emit(apiService.getCryptoCoins())
    }.flowOn(Dispatchers.IO)

}
