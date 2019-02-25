package com.raapp.wishlist.viewModels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SplashViewModel : ViewModel() {

    fun isUserLogedIn(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }
}