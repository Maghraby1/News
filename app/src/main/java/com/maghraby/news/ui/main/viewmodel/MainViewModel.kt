package com.maghraby.news.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maghraby.news.data.model.BaseResponse
import com.maghraby.news.ui.main.adapter.model.News
import com.maghraby.news.data.repository.MainRepository
import com.maghraby.news.ui.main.adapter.model.Country
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
    val countries = MutableLiveData<List<Country>>()

    var fetchByCountry = false

    init {
        fetchNews()
        fetchCountries()
    }

    private fun fetchCountries() {
        val countryList = ArrayList<Country>()
        var country = Country("Australia", "au")
        countryList.add(country)
        country = Country("Argentina", "ar")
        countryList.add(country)
        country = Country("Brazil", "br")
        countryList.add(country)
        country = Country("Canada", "ca")
        countryList.add(country)
        country = Country("China", "cn")
        countryList.add(country)
        country = Country("Egypt", "eg")
        countryList.add(country)
        country = Country("France", "fr")
        countryList.add(country)
        country = Country("Italy", "it")
        countryList.add(country)
        country = Country("Norway", "no")
        countryList.add(country)
        countries.postValue(countryList)
    }

    fun fetchNews() {
        if (fetchByCountry) {
            fetchByCountry = false
            offset = 0
        }
        viewModelScope.launch {
            _news.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getNews(offset).let {
                    offset += 25
                    handleNewsResponse(it)
                }
            } else _news.postValue(Resource.error("No internet connection", null))
        }
    }

    fun fetchNewsByCountry(countries: String) {
        if (!fetchByCountry) {
            fetchByCountry = true
            offset = 0
        }
        viewModelScope.launch {
            _news.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getNews(offset, countries).let {
                    offset += 25
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