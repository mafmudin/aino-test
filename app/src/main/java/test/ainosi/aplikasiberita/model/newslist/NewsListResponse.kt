package test.ainosi.aplikasiberita.model.newslist


import com.google.gson.annotations.SerializedName
import test.ainosi.aplikasiberita.base.BaseResponse

class NewsListResponse: BaseResponse(){
    @SerializedName("results")
    lateinit var news: List<News>
}