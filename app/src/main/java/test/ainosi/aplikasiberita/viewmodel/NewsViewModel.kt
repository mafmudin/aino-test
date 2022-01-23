package test.ainosi.aplikasiberita.viewmodel

import android.content.Context
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import test.ainosi.aplikasiberita.data.Resource
import test.ainosi.aplikasiberita.model.newslist.News
import test.ainosi.aplikasiberita.model.newslist.NewsListResponse
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

    fun internetCheck(context: Context){
        this.isInternet.value = Utility.isInternetAvailable(context)
    }

    fun getNewsTry(day:String): LiveData<Resource<MutableList<News>>> = Transformations.switchMap(isInternet){
        if (!it){
            AbsentLiveData.create()
        }else{
            newsRepository.getDailyNews(day)
        }
    }
}