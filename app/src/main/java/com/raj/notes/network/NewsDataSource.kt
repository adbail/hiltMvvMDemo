package com.raj.notes.network

import com.raj.notes.utils.ApiResult
import com.raj.notes.model.Article

interface NewsDataSource {
    suspend fun getNewsList() : ApiResult<List<Article>>
}