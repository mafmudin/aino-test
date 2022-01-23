package test.ainosi.aplikasiberita.repository

import androidx.lifecycle.LiveData
import test.ainosi.aplikasiberita.data.Resource
import test.ainosi.aplikasiberita.model.newslist.News

interface NewsRepository {
    fun getDailyNews(day:String):LiveData<Resource<MutableList<News>>>
}