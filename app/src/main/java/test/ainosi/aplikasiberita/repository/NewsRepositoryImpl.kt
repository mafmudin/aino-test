package test.ainosi.aplikasiberita.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.withContext
import test.ainosi.aplikasiberita.BuildConfig
import test.ainosi.aplikasiberita.base.BaseRepository
import test.ainosi.aplikasiberita.data.NetworkBoundResource
import test.ainosi.aplikasiberita.data.Resource
import test.ainosi.aplikasiberita.data.local.NewsDao
import test.ainosi.aplikasiberita.data.remote.ApiResponse
import test.ainosi.aplikasiberita.data.remote.ApiService
import test.ainosi.aplikasiberita.model.newslist.News
import test.ainosi.aplikasiberita.model.newslist.NewsListResponse
import test.ainosi.aplikasiberita.utility.ContextProviders
import java.lang.Exception
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val contextProviders: ContextProviders,
    private val newsDao: NewsDao,
    private val apiService: ApiService
):BaseRepository(), NewsRepository {
    override fun getDailyNews(day:String): LiveData<Resource<MutableList<News>>> {
        return object : NetworkBoundResource<MutableList<News>, NewsListResponse>(contextProviders){
            override fun loadFromDB(): LiveData<MutableList<News>> = newsDao.getLocalNews()

            override fun shouldFetch(data: MutableList<News>?): Boolean = data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<NewsListResponse>> {
                return liveData {
                    withContext(contextProviders.IO){
                        lateinit var response: NewsListResponse
                        try {
                            val apiKey = BuildConfig.API_KEY
                            response = execute(apiService.getNews(day, apiKey))
                            emit(ApiResponse.success(response))
                        }catch (e: Exception){
                            response = NewsListResponse()
                            response.message = e.message.toString()
                            emit(ApiResponse.error(e.message.toString(),response))
                        }
                    }
                }
            }

            override fun saveCallResult(data: NewsListResponse) {
                newsDao.insertNews(data.news)
            }

        }.asLiveData()
    }
}