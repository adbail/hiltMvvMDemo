package com.raj.notes.ui.main.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.raj.notes.utils.AppUtils
import com.raj.notes.ui.main.newslist.NewsAdapter
import com.raj.notes.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var adapterNews: NewsAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val linearLayoutManager = LinearLayoutManager(this)
        binding.recyNewList.layoutManager = linearLayoutManager
        adapterNews = NewsAdapter(mutableListOf())
        binding.recyNewList.adapter = adapterNews

        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        viewModel.getNewItem()
        bindObserver()

    }

    private fun bindObserver() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch(Dispatchers.Main) {
                    viewModel.article.collectLatest {
                        if (it.isNotEmpty()) {
                            adapterNews.addNewList(it)
                        }
                    }
                }

                launch {
                    viewModel.errorMessage.collectLatest {
                        if (it.isNotEmpty()) {
                            binding.progressBarNewsList.visibility = View.GONE
                            AppUtils().showToast(this@MainActivity, "error occurred")
                        }
                    }
                }

                launch {
                    viewModel.isLoading.collectLatest {
                        if (it) {
                            binding.progressBarNewsList.visibility = View.VISIBLE
                        } else {
                            binding.progressBarNewsList.visibility = View.GONE
                        }
                    }
                }

            }
        }
    }
}