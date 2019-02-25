package com.raapp.wishlist.dependencyInjection

import com.raapp.wishlist.viewModels.SplashViewModel
import com.raapp.wishlist.viewModels.WishListViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { WishListViewModel(get(), get()) }
    viewModel { SplashViewModel() }
}