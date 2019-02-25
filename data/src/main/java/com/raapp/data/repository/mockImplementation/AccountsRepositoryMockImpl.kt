package com.raapp.data.repository.mockImplementation

import androidx.paging.DataSource
import com.raapp.data.logLine
import com.raapp.data.models.Account
import com.raapp.data.repository.AccountsRepository

class AccountsRepositoryMockImpl : AccountsRepository {
    override fun getFriendsLocal(): DataSource.Factory<Int, Account> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeFriendLocal() {
        logLine("launched")
    }

    override fun addFriendLocal(vararg account: Account) {
        logLine("launched")
    }
}