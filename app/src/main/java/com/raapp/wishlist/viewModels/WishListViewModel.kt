package com.raapp.wishlist.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.raapp.data.models.Wish
import com.raapp.data.repository.WishRepository

class WishListViewModel(
    application: Application,
    private val wishRepository: WishRepository
) : AndroidViewModel(application) {

    fun getWishList(): LiveData<List<Wish>> {
        return wishRepository.getAllWishesLocal()
    }
}