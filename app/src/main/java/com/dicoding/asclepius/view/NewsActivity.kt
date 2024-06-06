package com.dicoding.asclepius.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.adapter.AdapterNews
import com.dicoding.asclepius.data.remote.model.ArticlesItem
import com.dicoding.asclepius.databinding.ActivityNewsBinding
import com.dicoding.asclepius.viewmodel.NewsViewModel

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var adapterNews: AdapterNews
    private val vmNews: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle()
        setRecyclerView()

        vmNews.listNews.observe(this) { listNews ->
            setNewsData(listNews)
        }

        vmNews.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun setTitle() {
        val title = getString(R.string.news)
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvNews.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvNews.addItemDecoration(itemDecoration)

        adapterNews = AdapterNews()
        binding.rvNews.adapter = adapterNews
    }

    private fun setNewsData(newsData: List<ArticlesItem>) {
        adapterNews.submitList(newsData)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbNews.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}