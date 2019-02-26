package com.raapp.data.repository

import androidx.paging.DataSource
import com.raapp.data.models.Account

interface AccountsRepository {
    fun getFriendsLocal(): DataSource.Factory<Int, Account>
    fun removeFriendLocal()
    fun addFriendLocal(vararg account: Account)
}

