package com.raapp.wishlist.extentions

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.raapp.wishlist.R

fun Activity?.findMainNavController(): NavController? {
    return this?.let {
        Navigation.findNavController(it, R.id.nav_host_fragment)
    }
}