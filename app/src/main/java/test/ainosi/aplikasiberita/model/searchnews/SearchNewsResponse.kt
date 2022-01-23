package test.ainosi.aplikasiberita.model.searchnews


import com.google.gson.annotations.SerializedName

data class SearchNewsResponse(
    @SerializedName("copyright")
    var copyright: String?,
    @SerializedName("response")
    var response: Response?,
    @SerializedName("status")
    var status: String?
)