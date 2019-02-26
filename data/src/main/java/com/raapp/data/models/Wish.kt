package com.raapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raapp.data.Constants.EMPTY_STRING
import java.io.Serializable

@Entity(tableName = "wish")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String = EMPTY_STRING,
    var privacy: Int = PrivacyType.PRIVATE.ordinal,
    var link: String? = null,
    var description: String? = null
) : Serializable

enum class PrivacyType {
    PRIVATE, FRIENDS, PUBLIC;

    companion object {
        fun getById(id: Int): PrivacyType {
            return if (id < 0 || id > 2) PrivacyType.PRIVATE else PrivacyType.values()[id]
        }
    }
}

