package test.ainosi.aplikasiberita.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import test.ainosi.aplikasiberita.model.newslist.NewsListResponse
import test.ainosi.aplikasiberita.model.searchnews.SearchNewsResponse


interface ApiService {
    @GET("mostpopular/v2/viewed/{path}.json")
    fun getNews(
        @Path("path") day:String,
        @Query("api-key") apiKey:String
    ): Call<NewsListResponse>

    @GET("search/v2/articlesearch.json")
    fun searchNews(
        @Query("api-key") apiKey:String,
        @Query("q") query:String,
        @Query("page") page:Int
    ): Call<SearchNewsResponse>
}