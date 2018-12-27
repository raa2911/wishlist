package com.raapp.wishlist.view


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.raapp.wishlist.BaseFragment
import com.raapp.wishlist.Constants.LOG_TAG

import com.raapp.wishlist.R
import java.util.*
import kotlin.concurrent.schedule


/**
 * A simple [Fragment] subclass.
 *
 */
class SplashFragment : BaseFragment() {
    private var timer: Timer? = null
    private val splashDelay = 2000L
    private var firebaseUser: FirebaseUser? = null
    private val RC_SIGN_IN = 101

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseUser = FirebaseAuth.getInstance().currentUser
    }

    override fun onStart() {
        super.onStart()
        startTimer()
    }

    override fun onStop() {
        super.onStop()
        stopTimer()
    }

    private fun startTimer() {
        timer = Timer()
        timer?.schedule(splashDelay) {
            if (firebaseUser != null) {
                this@SplashFragment.findNavController().navigate(R.id.action_splashFragment_to_wishListFragment)
            } else {
                startFirebaseAuthActivity()
            }
        }
    }

    private fun startFirebaseAuthActivity() {
        val apiInstance = GoogleApiAvailability.getInstance()
        val response = apiInstance.isGooglePlayServicesAvailable(context)
        logMessage("startFirebaseAuthActivity isGooglePlayServicesAvailable = $response")
        logMessage(
            "startFirebaseAuthActivity isGooglePlayServicesAvailable = ${apiInstance.getErrorString(
                response
            )}"
        )
        if (response == ConnectionResult.SUCCESS) {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.PhoneBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build()
            )
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                RC_SIGN_IN
            )
        } else {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        logMessage("onActivityResult is start")
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            logMessage("onActivityResult RC_SIGN_IN, response is $response")
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                logMessage("onActivityResult user is $user")
                this@SplashFragment.findNavController().navigate(R.id.action_splashFragment_to_wishListFragment)
            } else {
                startFirebaseAuthActivity()
                logMessage("onActivityResult has error")
            }
        }
    }

    private fun stopTimer() {
        timer?.cancel()
        timer?.purge()
        timer = null
    }
}
