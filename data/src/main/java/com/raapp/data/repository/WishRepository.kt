package com.raapp.data.repository

import com.raapp.data.models.Wish

interface WishRepository {

    fun addNewWishLocal(wish: Wish)

    fun getAllWishesLocal(): List<Wish>
}

