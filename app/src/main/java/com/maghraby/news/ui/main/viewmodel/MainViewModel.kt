package com.maghraby.news.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maghraby.news.data.model.BaseResponse
import com.maghraby.news.data.model.News
import com.maghraby.news.data.repository.MainRepository
import com.maghraby.news.utils.NetworkHelper
import com.maghraby.news.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    var offset = 0
    private val _news = MutableLiveData<Resource<List<News>>>()
    val news: LiveData<Resource<List<News>>>
        get() = _news

    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            _news.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getNews(offset).let {
                    offset+=25
                    handleNewsResponse(it)
                }
            } else _news.postValue(Resource.error("No internet connection", null))
        }
    }

    private fun handleNewsResponse(it: Response<BaseResponse>) {
        if (it.isSuccessful) {
            _news.postValue(Resource.success(it.body()?.data))
        } else _news.postValue(Resource.error(it.errorBody().toString(), null))
    }
}