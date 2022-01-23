package test.ainosi.aplikasiberita.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import test.ainosi.aplikasiberita.model.newslist.Media
import java.lang.reflect.Type

class MediaConverter {
    @TypeConverter
    fun fromRole(value: String?): List<Media?>? {
        val listType: Type = object : TypeToken<List<Media?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<Media?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}