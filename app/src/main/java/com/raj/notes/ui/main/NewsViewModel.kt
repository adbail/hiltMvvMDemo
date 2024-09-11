package com.raj.notes.ui.main.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raj.notes.utils.ApiResult
import com.raj.notes.usecase.GetNewsUseCase
import com.raj.notes.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val getNewsUseCase: GetNewsUseCase) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    private val _Article = MutableStateFlow<List<Article>>(emptyList())
    val article = _Article.asStateFlow()

    fun getNewItem() {
        viewModelScope.launch {
            getNewsUseCase.execute().collectLatest { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> {
                        apiResult.data?.let { items ->
                            /*val data = items.map { article ->
                                Article(
                                    article.source,
                                    article.author ?: "",
                                    article.title ?: "",
                                    article.description ?: "",
                                    article.url ?: "",
                                    article.urlToImage ?: "",
                                    article.publishedAt ?: "",
                                    article.content ?: ""
                                )
                            }*/
                            _isLoading.emit(false)
                            _Article.emit(items)
                        }
                    }

                    is ApiResult.Error -> {
                        apiResult.message?.let {
                            _errorMessage.emit(it)
                        }
                    }

                    is ApiResult.Loading -> {
                        _isLoading.emit(true)
                    }
                }
            }
        }
    }
}