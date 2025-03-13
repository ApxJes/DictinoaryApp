package com.example.dictonaryapp.domain.repository

import com.example.dictonaryapp.data.repository.WordInfoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWordInfoRepository(
        wordInfoRepositoryImpl: WordInfoRepositoryImpl
    ): WordInfoRepository
}