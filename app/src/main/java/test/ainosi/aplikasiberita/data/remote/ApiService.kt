package test.ainosi.aplikasiberita.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import test.ainosi.aplikasiberita.model.newslist.NewsListResponse


interface ApiService {
    @GET("mostpopular/v2/viewed/{path}.json")
    fun getNews(
        @Path("path") day:String,
        @Query("api-key") apiKey:String
    ): Call<NewsListResponse>
}