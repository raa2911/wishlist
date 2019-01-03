package com.raapp.wishlist.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raapp.wishlist.Constants.EMPTY_STRING

@Entity(tableName = "wish")
data class Wish(
    var title: String = EMPTY_STRING,
//    @TypeConverters(PrivacyType::class)
//    var privacy: PrivacyType = PrivacyType.PRIVATE,
    var privacy: Int = PrivacyType.PRIVATE.ordinal,
    var link: String? = null,
    var description: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

enum class PrivacyType {
    PRIVATE, FRIENDS, PUBLIC;

    companion object {
        fun getById(id: Int): PrivacyType {
            return if (id < 0 || id > 2) PrivacyType.PRIVATE else PrivacyType.values()[id]
        }

//        @TypeConverter
//        @JvmStatic
//        fun getPrivacyType(id: Int): PrivacyType {
//            return getById(id)
//        }
//
//        @TypeConverter
//        @JvmStatic
//        fun getPrivacyTypeInt(type: PrivacyType): Int {
//            return type.ordinal
//        }
    }
}

