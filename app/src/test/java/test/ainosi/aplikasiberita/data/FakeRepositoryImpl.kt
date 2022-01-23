package test.ainosi.aplikasiberita.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import test.ainosi.aplikasiberita.BuildConfig
import test.ainosi.aplikasiberita.base.BaseRepository
import test.ainosi.aplikasiberita.data.local.NewsDao
import test.ainosi.aplikasiberita.data.remote.ApiService
import test.ainosi.aplikasiberita.model.newslist.NewsListResponse
import test.ainosi.aplikasiberita.model.searchnews.Response
import test.ainosi.aplikasiberita.model.searchnews.SearchNewsResponse
import test.ainosi.aplikasiberita.repository.NewsRepository
import test.ainosi.aplikasiberita.utility.ContextProviders
import javax.inject.Inject

class FakeRepositoryImpl @Inject constructor(
    private val contextProviders: ContextProviders,
    private val newsDao: NewsDao,
    private val apiService: ApiService
):BaseRepository(), NewsRepository {
    override fun getDailyNews(day:String): Deferred<NewsListResponse>{
        return CoroutineScope(contextProviders.IO).async {
            lateinit var response: NewsListResponse
            try {
                response = NewsListResponse()
                val resp = apiService.getNews(day, BuildConfig.API_KEY).execute()
                val result = resp.body()
                if (resp.isSuccessful && result != null){
                    response.responseStatus = "1"
                    response.news.addAll(result.news)
                }else{
                    response.responseStatus = "-1"
                    response.message = resp.errorBody().toString()
                }
            }catch (e: Exception){
                response = NewsListResponse()
                response.responseStatus = "-1"
                response.message = e.message.toString()
            }

            response
        }
    }

    override fun searchNews(query:String, page:Int): Deferred<SearchNewsResponse>{
        return CoroutineScope(contextProviders.IO).async {
            lateinit var response: SearchNewsResponse
            try {
                response = SearchNewsResponse()
                val resp = apiService.searchNews(BuildConfig.API_KEY, query, page).execute()
                val result = resp.body()
                if (resp.isSuccessful && result != null){
                    response.responseStatus = "1"
                    response.response = result.response
                }else{
                    response.responseStatus = "-1"
                    response.message = resp.errorBody().toString()
                }
            }catch (e: Exception){
                response = SearchNewsResponse()
                response.responseStatus = "-1"
                response.message = e.message.toString()
            }

            response
        }
    }
}