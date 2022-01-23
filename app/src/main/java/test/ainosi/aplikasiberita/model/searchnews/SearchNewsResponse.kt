package test.ainosi.aplikasiberita.model.searchnews


import com.google.gson.annotations.SerializedName
import test.ainosi.aplikasiberita.base.BaseResponse

class SearchNewsResponse: BaseResponse(){
    @SerializedName("response")
    var response: Response? = null
}