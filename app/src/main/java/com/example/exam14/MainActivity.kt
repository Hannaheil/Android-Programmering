package com.example.exam14

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.exam14.data.ProductRepository
import com.example.exam14.screens.favorite_list.FavoriteListScreen
import com.example.exam14.screens.favorite_list.FavoriteListViewModel
import com.example.exam14.screens.product_details.ProductDetailsScreen
import com.example.exam14.screens.product_details.ProductDetailsViewModel
//import com.example.exam14.screens.product_details.ProductDetailsScreen
//import com.example.exam14.screens.product_details.ProductDetailsViewModel
import com.example.exam14.screens.product_list.ProductListScreen
import com.example.exam14.screens.product_list.ProductListViewModel
import com.example.exam14.ui.theme.Exam14Theme

class MainActivity : ComponentActivity() {
   // private val _productDetailsViewModel: ProductDetailsViewModel by viewModels()
    private val _productListViewModel: ProductListViewModel by viewModels()
    private val _favoriteListViewModel: FavoriteListViewModel by viewModels()
    private val _productDetailsViewModel: ProductDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ProductRepository.initializeDatabase(applicationContext)

        setContent {
            Exam14Theme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "productListScreen"
                ) {
                    composable(
                        route = "productListScreen"
                    ) {
                        ProductListScreen(
                            viewModel = _productListViewModel,
                            onProductClick = { productId ->
                                navController.navigate("productDetailsScreen/$productId")
                            },
                            navigateToFavoriteList = {
                                navController.navigate("favoriteListScreen")
                            }
                        )

                    }

                    composable(
                        route = "productDetailsScreen/{productId}",
                        arguments = listOf(
                            navArgument(name = "productId") {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId") ?: -1

                        // LaunchedEffect will run it's code block whenever 'filmId' is updated
                        LaunchedEffect(productId) {
                            _productDetailsViewModel.setSelectedProduct(productId)
                        }

                        ProductDetailsScreen(
                            viewModel = _productDetailsViewModel,
                            onBackButtonClick = { navController.popBackStack() }
                        )
                    }

                    composable(route = "favoriteListScreen") {
                        // LaunchedEffect will run it's code block first time we navigate to favoriteListScreen
                        LaunchedEffect(Unit) {
                            _favoriteListViewModel.loadFavorites()
                        }

                        FavoriteListScreen(
                            viewModel = _favoriteListViewModel,
                            onBackButtonClick = { navController.popBackStack() },
                            onProductClick = { productId ->
                                navController.navigate("productDetailsScreen/$productId")
                            }
                        )
                    }
                  /*  composable(
                        route = "productDetailsScreen/{productId}",
                        arguments = listOf(
                            navArgument("productId"){
                            type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId")?: 0
                        LaunchedEffect(productId){
                            _productDetailsViewModel.setSelectedProduct(productId = productId)
                        }
                        ProductDetailsScreen(
                            viewModel = _productDetailsViewModel,
                            onBackButtonClick = {navController.popBackStack()}
                        )
                    }*/
                }
            }
        }
    }
}