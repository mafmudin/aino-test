package test.ainosi.aplikasiberita.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import test.ainosi.aplikasiberita.model.newslist.MediaMetadata
import java.lang.reflect.Type

class MediaMetadataConverter {
    @TypeConverter
    fun fromRole(value: String?): List<MediaMetadata?>? {
        val listType: Type = object : TypeToken<List<MediaMetadata?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<MediaMetadata?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}