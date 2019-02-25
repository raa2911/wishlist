package com.raapp.wishlist

import android.app.Application
import com.raapp.wishlist.dependencyInjection.mockRepositories
import com.raapp.wishlist.dependencyInjection.realRepositories
import com.raapp.wishlist.dependencyInjection.viewModelModule
import org.koin.android.ext.android.startKoin

class WishApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(
            this, listOf(
                if (BuildConfig.MOCK_REPOSITORY) mockRepositories else realRepositories,
                viewModelModule
            )
        )
    }
}