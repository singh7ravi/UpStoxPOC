package com.example.upstoxpoc.domain.utils


import com.example.upstoxpoc.data.model.CryptoItem
import retrofit2.Response

sealed class ApiState {

    class Success(val data: Response<List<CryptoItem>>) : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    object Loading : ApiState()
    object Empty : ApiState()
}