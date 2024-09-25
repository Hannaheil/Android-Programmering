package com.example.exam14.screens.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam14.data.Product
import com.example.exam14.data.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        // Async functions (Repository, data source) can ONLY be called within a coroutine-scope.
        // All ViewModels contains their own scope for this exact purpose, so we use it to call
        // our repository functions here.
        // As the functions are asynchronous, we can update our 'loading'-state inside the
        // coroutine-scope before calling the function and after receiving the receiving the response.
        // We then use this 'loading'-state to display a progress-indicator in our UI.
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _products.value = ProductRepository.getProducts()
            _loading.value = false
        }
    }
}

/*
class ProductListViewModel: ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    init{
        loadProducts()
    }
    fun loadProducts(){
        viewModelScope.launch(Dispatchers.IO){
            _loading.value = true
            _products.value = ProductRepository.getAllProducts()
            _loading.value = false
        }
    }
}*/