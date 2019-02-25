package com.raapp.data.repository.implementation

import androidx.paging.DataSource
import com.raapp.data.models.Account
import com.raapp.data.repository.AccountsRepository

class AccountsRepositoryImpl: AccountsRepository {
    override fun getFriendsLocal(): DataSource.Factory<Int, Account> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeFriendLocal() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addFriendLocal(vararg account: Account) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}