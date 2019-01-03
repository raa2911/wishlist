package com.raapp.wishlist.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.raapp.wishlist.models.Wish

@Dao
interface WishDao {

    @Query("SELECT * FROM wish")
    fun getAll(): List<Wish>

    @Query("SELECT * FROM wish WHERE uid IS (:wishId)")
    fun loadAllById(wishId: Int): Wish

//    @Insert
//    fun insertAll(vararg wishes: Wish)

    @Insert
    fun insert(wish: Wish)

    @Delete
    fun delete(wish: Wish)

}