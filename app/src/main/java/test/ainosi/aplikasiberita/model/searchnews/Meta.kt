package test.ainosi.aplikasiberita.model.searchnews


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("hits")
    var hits: Int?,
    @SerializedName("offset")
    var offset: Int?,
    @SerializedName("time")
    var time: Int?
)