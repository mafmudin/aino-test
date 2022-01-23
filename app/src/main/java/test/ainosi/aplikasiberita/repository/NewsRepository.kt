package test.ainosi.aplikasiberita.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import test.ainosi.aplikasiberita.data.Resource
import test.ainosi.aplikasiberita.model.newslist.News
import test.ainosi.aplikasiberita.model.newslist.NewsListResponse

interface NewsRepository {
    fun getDailyNews(day:String): Deferred<NewsListResponse>
}