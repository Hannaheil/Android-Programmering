package com.example.exam14.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
    @GET("products")
    //@GET("products?limit=5")
    //suspend fun getAllProducts(): Response<ProductResponseList>
    suspend fun getAllProducts(): Response<List<Product>>

    @GET("products/{id}")
    suspend fun getProduct(
        @Path("id") id: Int
    ): Response<Product>
    /*suspend fun getProductDetails(
        @Path("id") id: Int
    ): Response<ProductDetails>*/
}