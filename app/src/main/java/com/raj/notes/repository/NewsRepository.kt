package com.raj.notes.repository

import com.raj.notes.utils.ApiResult
import com.raj.notes.network.NewsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsDataSource: NewsDataSource) {
    suspend fun getNewsList() = flow {
        emit(ApiResult.Loading())

        val resource = newsDataSource.getNewsList()

        emit(resource)

    }.flowOn(Dispatchers.IO)

}