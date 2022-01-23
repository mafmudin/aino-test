package test.ainosi.aplikasiberita.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import test.ainosi.aplikasiberita.data.Resource
import test.ainosi.aplikasiberita.model.newslist.News
import test.ainosi.aplikasiberita.model.newslist.NewsListResponse
import test.ainosi.aplikasiberita.model.searchnews.Doc
import test.ainosi.aplikasiberita.repository.NewsRepository
import test.ainosi.aplikasiberita.utility.Utility
import test.ainosi.aplikasiberita.utility.livedata.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
):ViewModel() {
    private var isInternet = MutableLiveData(false)

    private val _result = MutableLiveData<Resource<MutableList<News>>>()
    val result: LiveData<Resource<MutableList<News>>> = _result

    fun internetCheck(context: Context){
        this.isInternet.value = Utility.isInternetAvailable(context)
    }

    fun getNewsTry(day:String) = viewModelScope.launch {
        _result.postValue(Resource.loading(null))

        try {
            val response = newsRepository.getDailyNews(day).await()
            Timber.e("RESPONSE %s", Gson().toJson(response))
            if (response.responseStatus == "1"){
                _result.postValue(Resource.success(response.news))
            }else{
                _result.postValue(Resource.error(null, response.message))
            }
        }catch (e: Exception){
            _result.postValue(Resource.error(null, e.message.toString()))
        }
    }

    private val _resultSearch = MutableLiveData<Resource<MutableList<Doc>>>()
    val resultSearch: LiveData<Resource<MutableList<Doc>>> = _resultSearch

    fun searchNews(query:String, page:Int) = viewModelScope.launch {
        _resultSearch.postValue(Resource.loading(null))

        try {
            val response = newsRepository.searchNews(query, page).await()
            Timber.e("RESPONSE %s", Gson().toJson(response))
            if (response.responseStatus == "1"){
                _resultSearch.postValue(Resource.success(response.docs))
            }else{
                _resultSearch.postValue(Resource.error(null, response.message))
            }
        }catch (e: Exception){
            _resultSearch.postValue(Resource.error(null, e.message.toString()))
        }
    }
}