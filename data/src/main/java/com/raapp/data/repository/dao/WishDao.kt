package com.raapp.data.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.raapp.data.models.Wish

@Dao
interface WishDao {

    @Query("SELECT * FROM wish")
    fun getAll(): LiveData<List<Wish>>

    @Query("SELECT * FROM wish WHERE id IS (:wishId)")
    fun loadById(wishId: Int): LiveData<Wish>

    @Insert
    fun insertAll(vararg wishes: Wish)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wish: Wish)

    @Delete
    fun delete(wish: Wish)

}