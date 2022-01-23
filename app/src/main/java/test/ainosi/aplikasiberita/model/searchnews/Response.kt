package test.ainosi.aplikasiberita.model.searchnews


import com.google.gson.annotations.SerializedName
import test.ainosi.aplikasiberita.base.BaseResponse

class Response:BaseResponse(){
    @SerializedName("docs")
    var docs: MutableList<Doc> = arrayListOf()
}