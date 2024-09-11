package com.raj.notes.di

import com.raj.notes.network.NetworkDataSource
import com.raj.notes.network.NewsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindNewsDataSource(networkDataSource: NetworkDataSource): NewsDataSource
}