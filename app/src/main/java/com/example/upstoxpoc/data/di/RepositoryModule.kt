package com.example.upstoxpoc.data.di

import com.example.upstoxpoc.data.network.ApiService
import com.example.upstoxpoc.domain.repository.CryptoCoinRepository
import com.example.upstoxpoc.domain.repository.CryptoCoinRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCryptoCoinRepository(apiService: ApiService): CryptoCoinRepository {
        return CryptoCoinRepositoryImpl(apiService)
    }
}
