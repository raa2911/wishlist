package com.raapp.wishlist.repository.dao

import androidx.room.*
import com.raapp.wishlist.models.Wish

@Dao
interface WishDao {

    @Query("SELECT * FROM wish")
    fun getAll(): List<Wish>

    @Query("SELECT * FROM wish WHERE id IS (:wishId)")
    fun loadAllById(wishId: Int): Wish

//    @Insert
//    fun insertAll(vararg wishes: Wish)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wish: Wish)

    @Delete
    fun delete(wish: Wish)

}