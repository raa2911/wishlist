package com.raapp.wishlist.view


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
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

import com.raapp.wishlist.R
import java.util.*
import kotlin.concurrent.schedule


/**
 * A simple [Fragment] subclass.
 *
 */
class SplashFragment : BaseFragment() {
    private var timer: Timer? = null
    private val splashDelay = 1000L
    private val authHandlerDelay = 200L
    private var firebaseUser: FirebaseUser? = null
    private val RC_SIGN_IN = 101
    private val RC_ERROR_DIALOG = 102
    private var handler: Handler? = null

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
        clearAuthHandler()
    }

    private fun startTimer() {
        timer = Timer()
        timer?.schedule(splashDelay) {
            if (firebaseUser != null) {
                this@SplashFragment.findNavController().navigate(R.id.action_splashFragment_to_wishListFragment)
            } else {
                initFirebaseAuthActivity()
            }
        }
    }

    private fun initFirebaseAuthActivity() {
        val apiInstance = GoogleApiAvailability.getInstance()
        val authStatusCode = apiInstance.isGooglePlayServicesAvailable(context)
        logMessage("initFirebaseAuthActivity isGooglePlayServicesAvailable = $authStatusCode")
        logMessage(
            "initFirebaseAuthActivity isGooglePlayServicesAvailable = ${apiInstance.getErrorString(
                authStatusCode
            )}"
        )
        if (authStatusCode == ConnectionResult.SUCCESS) {
            showFirebaseAuthActivity()
        } else {
            showAuthErrorDialog(apiInstance, authStatusCode)
        }
    }

    private fun showFirebaseAuthActivity() {
        clearAuthHandler()
        handler = Handler()
        handler?.postDelayed({
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
        }, authHandlerDelay)
    }

    private fun showAuthErrorDialog(
        apiInstance: GoogleApiAvailability,
        authStatusCode: Int
    ) {
        activity?.runOnUiThread {
            apiInstance.showErrorDialogFragment(activity, authStatusCode, RC_ERROR_DIALOG) {
                initFirebaseAuthActivity()
            }
        }
    }

    private fun clearAuthHandler() {
        handler?.removeCallbacksAndMessages(null)
        handler = null
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
                initFirebaseAuthActivity()
                logMessage("onActivityResult has error")
            }
        }

        if (requestCode == RC_ERROR_DIALOG) {
            initFirebaseAuthActivity()
        }
    }

    private fun stopTimer() {
        timer?.cancel()
        timer?.purge()
        timer = null
    }
}
