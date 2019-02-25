package com.raapp.data.repository

import androidx.lifecycle.LiveData
import com.raapp.data.models.Wish

interface WishRepository {

    fun addNewWishLocal(wish: Wish)

    fun getAllWishesLocal(): LiveData<List<Wish>>
}

