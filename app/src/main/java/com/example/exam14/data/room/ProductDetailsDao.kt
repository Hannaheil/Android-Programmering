package com.example.exam14.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
//import com.example.exam14.data.ProductDetails
/*
@Dao
interface ProductDetailsDao {
    @Query("SELECT * FROM ProductDetails WHERE :productId = id")
    suspend fun getProductById(productId: Int): ProductDetails?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductDetail(vararg productDetails: ProductDetails)
}*/