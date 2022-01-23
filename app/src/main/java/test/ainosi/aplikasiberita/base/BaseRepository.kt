package test.ainosi.aplikasiberita.base

import android.util.Log
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import test.ainosi.aplikasiberita.BuildConfig
import timber.log.Timber

abstract class BaseRepository {
    companion object {
        private const val TAG = "BaseRepository"

        fun<T : BaseResponse> execute(call : Call<T>) : T {
            try{
                val response = call.execute()
                return when(response.isSuccessful){
                    true -> {
                        if(BuildConfig.BUILD_TYPE == ("debug"))
                            Log.d(TAG, Gson().toJson(response.body()!!))
                        response.body()!!
                    }
                    false -> {
                        if(BuildConfig.BUILD_TYPE == "debug")
                            Log.d(TAG, response.message())
                        throw HttpException(response)
                    }
                }
            }
            catch (e : Exception){
                if(BuildConfig.BUILD_TYPE == "debug")
                    e.message?.let {
                        Log.d(TAG, it)
                    }
                throw e
            }
        }

        fun Response<*>.getError(): Throwable {
            val errorBody = errorBody()?.string() ?: ""
            Timber.e("ERROR BODY %s", errorBody)
            return Throwable(
                try {
                    val json = JSONObject(errorBody)
                    json.getString("message")
                } catch (e: Exception) {
                    errorBody
                }
            )
        }
    }
}