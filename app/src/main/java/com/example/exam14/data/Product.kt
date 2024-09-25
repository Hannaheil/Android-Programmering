package com.example.exam14.data

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.exam14.data.room.ProductTypeConverter
import com.google.gson.annotations.SerializedName


@Entity
//@TypeConverters(ProductTypeConverter ::class)
@TypeConverters(ProductTypeConverter::class)
data class Product(
    @PrimaryKey
    val title: String,
    val id: Int,
    val price: Double,
    val category: String, // Ny egenskap for kategorien
    val description: String,
    //val imageUrl: String?
    @SerializedName("image")
    //val image: ImageUrl,
    val ImageUrl: String
)

//data class ImageUrl(val original: String)

/*
@Entity
data class Product(
    @PrimaryKey
    val title: String,
    val id: Int,
    val url: String,
    val imageUrl: String?, // We make this one ourself
)

data class ProductResponseList(val results: List<Product>)*/