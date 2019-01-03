package com.raapp.wishlist.repository

import android.content.Context
import androidx.room.CoroutinesRoom
import androidx.room.Room
import com.raapp.wishlist.models.Wish
import com.raapp.wishlist.repository.dao.AppDatabase
import kotlinx.coroutines.*
import java.util.concurrent.Callable
import kotlin.coroutines.CoroutineContext

interface WishRepository {

    fun addNewWishLocal(wish: Wish)

    fun getAllWishesLocal(): List<Wish>
}

class WishRepositoryImpl(context: Context) : WishRepository {
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-wish"
    )
        .fallbackToDestructiveMigration()
        .build()

    override fun addNewWishLocal(wish: Wish) {
        GlobalScope.launch {
            db.wishDao().insert(wish)
        }
    }

    override fun getAllWishesLocal(): List<Wish> {
        return db.wishDao().getAll()
    }

    companion object {
        var INSTANCE: WishRepository? = null

        @JvmStatic
        fun getInstance(context: Context): WishRepository {
            if (INSTANCE == null) {
                INSTANCE = WishRepositoryImpl(context)
            }
            return INSTANCE!!
        }
    }
}