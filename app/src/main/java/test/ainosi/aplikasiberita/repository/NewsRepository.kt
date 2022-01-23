package test.ainosi.aplikasiberita.repository

import kotlinx.coroutines.Deferred
import test.ainosi.aplikasiberita.model.newslist.NewsListResponse
import test.ainosi.aplikasiberita.model.searchnews.Response

interface NewsRepository {
    fun getDailyNews(day:String): Deferred<NewsListResponse>
    fun searchNews(query:String, page:Int): Deferred<Response>
}