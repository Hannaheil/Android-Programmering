package com.example.exam14.screens.favorite_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam14.data.Product
import com.example.exam14.data.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteListViewModel : ViewModel() {

    private val _favoriteProducts = MutableStateFlow<List<Product>>(emptyList())
    val favoriteProducts = _favoriteProducts.asStateFlow()

    fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfFavoriteIds = ProductRepository.getFavorites().map { it.productId }
            _favoriteProducts.value = ProductRepository.getProductsByIds(listOfFavoriteIds)
        }
    }
}