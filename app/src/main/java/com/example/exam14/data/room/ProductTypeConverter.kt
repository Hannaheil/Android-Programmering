package com.example.exam14.data.room

import androidx.room.TypeConverter
//import com.example.exam14.data.ImageUrl
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ProductTypeConverter {
    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun fromStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromListString(list: List<String>): String {
        return gson.toJson(list)
    }

    /*
    // Converter for ImageUrl (image)
    @TypeConverter
    @JvmStatic
    fun fromImageUrlString(value: String): ImageUrl {
        return gson.fromJson(value, ImageUrl::class.java)
    }

    @TypeConverter
    @JvmStatic
    fun fromImageUrl(imageUrl: ImageUrl): String {
        return gson.toJson(imageUrl)
    }*/
}
