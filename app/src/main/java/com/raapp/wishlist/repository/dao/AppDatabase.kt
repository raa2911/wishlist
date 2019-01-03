package com.raapp.wishlist.repository.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raapp.wishlist.models.Wish

@Database(entities = [Wish::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wishDao(): WishDao
}