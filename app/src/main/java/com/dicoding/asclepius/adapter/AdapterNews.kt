package com.dicoding.asclepius.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.remote.model.ArticlesItem
import com.dicoding.asclepius.databinding.ItemNewsBinding

class AdapterNews : ListAdapter<ArticlesItem, AdapterNews.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MyViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticlesItem) {
            binding.tvTitleNews.text = item.title
            binding.tvDescNews.text = item.description
            Glide.with(itemView.context)
                .load(item.urlToImage)
                .apply(
                    RequestOptions
                        .centerCropTransform()
                        .placeholder(R.drawable.baseline_refresh_24)
                        .error(R.drawable.baseline_broken_image_24)
                )
                .into(binding.ivNews)
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}