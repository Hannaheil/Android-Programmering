package com.example.exam14.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.exam14.data.room.AppDatabase
import com.example.exam14.data.room.Favorite
import com.example.exam14.data.room.FavoriteDao
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ProductRepository {
    private val _httpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
    private val _retrofit =
        Retrofit.Builder()
            .client(_httpClient)
            //NB! husk https pga sikkerhets filter
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val _productService = _retrofit.create(ProductService::class.java)

    //DB
    private lateinit var _appDatabase: AppDatabase
    private val _productDao by lazy { _appDatabase.productDao() }
    //private val _productDetailsDao by lazy { _appDatabase.productDetailsDao() }
    private val _favoriteDao by lazy { _appDatabase.favoriteDao() }

    fun initializeDatabase(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "app-database"
        ).fallbackToDestructiveMigration().build()//.build()
    }
    suspend fun getProducts(): List<Product> {
        try {
            val response = _productService.getAllProducts()

            Log.d("ProductRepository", "$response")


            if (response.isSuccessful) {
                // The "response body" might be NULL (the API response was successful, but didn't
                // return any data (NULL) -- If we don't handle this, we might also run into a crash!!
                val products = response.body() ?: emptyList()

                // Here we insert the resulting films from the API into our database!!
                _productDao.insertProducts(products)

                // We retrieve the data only from our DB, making our app support offline mode..
                return _productDao.getProducts()
            } else {
                throw Exception("Response was not successful")
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "Failed to get list of product", e)
            return _productDao.getProducts()
        }
    }


   /* suspend fun getAllProducts(): List<Product>{
        try{
            val response = _productService.getAllProducts()

            if(!response.isSuccessful){
                val products = response.body() ?: emptyList()

                _productDao.insertProducts(products)

                return _productDao.getAllProducts()
            }else{
                Log.e("ProductRepository", "Response was not successful: ${response.code()}")
                //return emptyList()
                throw Exception("Response was not successful: HTTP ${response.code()}")
            }
        }catch (e: Exception){
            Log.e("ProductRepository", "Failed to get list of products", e)
            return _productDao.getAllProducts()

        }
    }*/
    suspend fun getProductById(productId: Int): Product? {
        return _productDao.getProductById(productId)
    }

    suspend fun getProductsByIds(idList: List<Int>): List<Product> {
        return _productDao.getProductsByIds(idList)
    }

    // --- Favorite Functions ---

    suspend fun getFavorites(): List<Favorite> {
        return _favoriteDao.getFavorites()
    }

    suspend fun addFavorite(favorite: Favorite) {
        _favoriteDao.insertFavorite(favorite)
    }

    suspend fun removeFavorite(favorite: Favorite){
        _favoriteDao.removeFavorite(favorite)
    }
    /*suspend fun getAllProducts(): List<Product>{
        try{
            val response = _productService.getAllProducts()

            if(!response.isSuccessful){

                /Log.e("ProductRepository", "Failed to get list of products. HTTP ${response.code()}")
                throw Exception("Response was not successful: HTTP ${response.code()}")
            }
            val products = response.body()?.results?.map {product ->
                val idOfImage =
                    product.url.removePrefix("https://fakestoreapi.com/").removeSuffix("/")
                val imageUrl =
                    "https://fakestoreapi.com/img/$idOfImage.jpg"
                product.copy(imageUrl = imageUrl)
            } ?: throw Exception("response.body() was empty")

            _productDao.insertProducts(products)
            return _productDao.getAllProducts()
        }catch (e: Exception){
            Log.e("ProductRepository", "Failed to get list of products", e)
            return _productDao.getAllProducts()

        }
    }*/
/*
    suspend fun getProductDetails(id: Int): ProductDetails?{
        try{
            val response = _productService.getProductDetails(id)
            if(!response.isSuccessful){
                //throw Exception("Response was successful: HTTP ${response.code()}")
            }
            val productDetails = response.body() ?: throw Exception("response.body() was empty")
            val imageUrl= "https://fakestoreapi.com/img/${productDetails.id}.jpg"

            //Updates the imageUrl to the real one
            _productDetailsDao.insertProductDetail(productDetails.copy(imageUrl = imageUrl))
            return _productDetailsDao.getProductById(id)
        }catch (e: Exception){
            Log.e("ProductRepsitory", "Failed to get productDetails", e)
            return _productDetailsDao.getProductById(id)
        }
    }*/

}