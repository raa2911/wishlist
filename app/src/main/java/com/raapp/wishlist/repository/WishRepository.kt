package com.raapp.wishlist.repository

import android.content.Context
import androidx.room.Room
import com.raapp.wishlist.models.Wish
import com.raapp.wishlist.repository.dao.AppDatabase

interface WishRepository {

    fun addNewWishLocal(wish: Wish)

    fun getAllWishesLocal(): List<Wish>
}

class WishRepositoryImpl(context: Context) : WishRepository {
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-wish"
    ).build()

    override fun addNewWishLocal(wish: Wish) {
        db.wishDao().insert(wish)
    }

    override fun getAllWishesLocal(): List<Wish> {
        return db.wishDao().getAll()
    }

}