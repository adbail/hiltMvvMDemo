package com.raj.notes.usecase

import com.raj.notes.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend fun execute() = newsRepository.getNewsList()
}