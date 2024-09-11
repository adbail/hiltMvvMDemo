package com.raj.notes.ui.main.newslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raj.notes.databinding.ItemNewsBinding
import com.raj.notes.model.Article


class NewsAdapter(private var list: MutableList<Article>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    fun addNewList(lists : List<Article>){
        val lastSize = list.size
        list.addAll(lists)
        notifyItemRangeInserted(lastSize, list.size)
    }

    inner class NewsViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.tvTitle.text = article.title
            binding.tvDesc.text = article.description
            binding.tvAuthor.text = article.author
            binding.tvPublishedAt.text = article.publishedAt

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val bindings = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(bindings)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

}
