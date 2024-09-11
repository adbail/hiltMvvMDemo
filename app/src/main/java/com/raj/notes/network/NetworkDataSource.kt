package com.raj.notes.network

import com.raj.notes.utils.ApiResult
import com.raj.notes.model.Article
import com.raj.notes.model.NewsResponse
import retrofit2.Response
import java.net.SocketTimeoutException
import javax.inject.Inject

class NetworkDataSource @Inject constructor(private val apiService: ApiService) : NewsDataSource {
    override suspend fun getNewsList(): ApiResult<List<Article>> {
        return apiService.getTopHeadlines("US", "78770450a9a34ba0bbfa7df9c1805a90").let { response ->
            handleResponse(response)
        }
    }
    private fun handleResponse(response: Response<NewsResponse>): ApiResult<List<Article>> {
        return try {
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.articles != null) {
                    ApiResult.Success(body.articles)
                } else {
                    ApiResult.Error("An unknown error occurred")
                }
            } else {
                ApiResult.Error(response.message())
            }
        } catch (e: Exception) {
            if (e is SocketTimeoutException){
                ApiResult.Error(e.message ?: "Api Timeout")

            }else{
                ApiResult.Error(e.message ?: "An unknown error occurred")
            }


        }
    }
}