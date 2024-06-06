package com.dicoding.asclepius.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.BuildConfig.NEWS_API_KEY
import com.dicoding.asclepius.data.remote.model.ArticlesItem
import com.dicoding.asclepius.data.remote.model.NewsResponse
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import com.dicoding.asclepius.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listNews = MutableLiveData<List<ArticlesItem>>()
    val listNews: LiveData<List<ArticlesItem>> = _listNews

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    init {
        showNews()
    }

    fun showNews() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getNews(NEWS_API_KEY)
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listNews.value = responseBody.articles
                    }
                } else {
                    _isLoading.value = false
                    val errorMessage = "Terjadi kesalahan: ${response.message()}"
                    showToast(errorMessage)
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                _isLoading.value = false
                val errorMessage = "Terjadi kesalahan: ${t.message}"
                showToast(errorMessage)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showToast(msg: String) {
        _toastText.value = Event(msg)
    }

    companion object {
        private const val TAG = "NewsViewModel"
    }
}

