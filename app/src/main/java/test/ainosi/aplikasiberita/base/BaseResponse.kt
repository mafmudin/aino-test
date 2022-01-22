package test.ainosi.aplikasiberita.base

import com.google.gson.annotations.SerializedName


abstract class BaseResponse {
    var responseStatus : String = ""
    var message : String = ""
    @SerializedName("status")
    lateinit var status: String
}