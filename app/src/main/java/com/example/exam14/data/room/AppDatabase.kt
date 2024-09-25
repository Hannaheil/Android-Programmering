package com.example.exam14.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.exam14.data.Product

@Database(
    // entities = [Product::class, ProductDetails::class],
    entities = [Product::class, Favorite::class],
    version = 7,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase(){
    abstract fun productDao(): ProductDao
    //abstract fun productDetailsDao(): ProductDetailsDao
    abstract fun favoriteDao(): FavoriteDao
}