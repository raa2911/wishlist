package com.raapp.wishlist.dependencyInjection

import com.raapp.data.repository.AccountsRepository
import com.raapp.data.repository.WishRepository
import com.raapp.data.repository.implementation.AccountsRepositoryImpl
import com.raapp.data.repository.implementation.WishRepositoryImpl
import com.raapp.data.repository.mockImplementation.AccountsRepositoryMockImpl
import com.raapp.data.repository.mockImplementation.WishRepositoryMockImpl
import org.koin.dsl.module.module

val mockRepositories = module {
    single<WishRepository> { WishRepositoryMockImpl() }
    single<AccountsRepository> { AccountsRepositoryMockImpl() }
}

val realRepositories = module {
    single<WishRepository> { WishRepositoryImpl(get()) }
    single<AccountsRepository> { AccountsRepositoryImpl() }
}