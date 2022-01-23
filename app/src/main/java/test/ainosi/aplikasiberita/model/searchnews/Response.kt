package test.ainosi.aplikasiberita.model.searchnews


import com.google.gson.annotations.SerializedName
import test.ainosi.aplikasiberita.base.BaseResponse

data class Response(
    @SerializedName("docs")
    var docs: MutableList<Doc>
)