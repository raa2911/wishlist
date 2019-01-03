package com.raapp.wishlist.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raapp.wishlist.Constants.EMPTY_STRING

@Entity
data class Wish(
    @PrimaryKey var uid: Int,
    var title: String = EMPTY_STRING,
    var privacy: Int = 0,
    var link: String? = null,
    var description: String? = null
)