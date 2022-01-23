package test.ainosi.aplikasiberita.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import test.ainosi.aplikasiberita.model.newslist.Media
import java.lang.reflect.Type

class StringListConverter {
    @TypeConverter
    fun fromRole(value: String?): List<String?>? {
        val listType: Type = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<String?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}