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

class WishRepositoryMockImpl : WishRepository {
    override fun addNewWishLocal(wish: Wish) {
        // do nothing
    }

    override fun getAllWishesLocal(): List<Wish> {
        return listOf(
            Wish(
                title = "Title 1",
                description = "Description 1",
                privacy = 0
            ),
            Wish(
                title = "Title 2",
                description = "Description 1",
                privacy = 1
            ),
            Wish(
                title = "Title 3",
                description = "Description 1",
                privacy = 2
            ),
            Wish(
                title = "Title 4",
                description = "Description 1",
                privacy = 3
            ),
            Wish(
                title = "Title 5",
                description = "Description 1",
                privacy = 1
            ),
            Wish(
                title = "Title 1, title  title  title  title  title  title  title  title  title  title  title  title  title  title  title  title  title ",
                description = "Description? werrrrrry long dexcription   text text text text text text text text text text text text text text text text text text text text text ",
                privacy = -1
            ),
            Wish(
                title = "Title 1",
                description = "Description 1"
            ),
            Wish(
                title = "Title 2",
                description = "Description 1",
                privacy = 0
            ),
            Wish(
                title = "Title 3",
                description = "Description 1",
                privacy = 0
            )
        )

    }

}