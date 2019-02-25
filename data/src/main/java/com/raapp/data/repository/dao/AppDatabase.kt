package com.raapp.data.repository.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raapp.data.models.Wish

@Database(entities = [Wish::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wishDao(): WishDao
}