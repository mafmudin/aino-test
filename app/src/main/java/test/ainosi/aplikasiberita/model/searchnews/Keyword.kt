package test.ainosi.aplikasiberita.model.searchnews


import com.google.gson.annotations.SerializedName

data class Keyword(
    @SerializedName("major")
    var major: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("rank")
    var rank: Int?,
    @SerializedName("value")
    var value: String?
)