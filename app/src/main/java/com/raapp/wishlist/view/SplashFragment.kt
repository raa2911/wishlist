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
import com.raapp.wishlist.BaseFragment

import com.raapp.wishlist.R
import com.raapp.wishlist.viewModels.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.concurrent.schedule


/**
 * A simple [Fragment] subclass.
 *
 */
class SplashFragment : BaseFragment<SplashViewModel>() {
    private var timer: Timer? = null
    private var handler: Handler? = null
    override val viewModels: SplashViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        startTimer()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
        clearAuthHandler()
    }

    /*
     * Start timer with Firebase user check.
     * If user exist -> transition to main application fragment,
     * if not -> start Firebase auth process
     */
    private fun startTimer() {
        timer = Timer()
        timer?.schedule(SPLASH_DELAY) { nextScreen() }
    }

    private fun nextScreen() {
        if (viewModels.isUserLogedIn()) {
            findNavController().navigate(R.id.action_splashFragment_to_wishListFragment)
        } else {
            initFirebaseAuthUI()
        }
    }

    /*
     * Check Firebase Auth (Play) services availability.
     * If available -> show Firebase Auth UI,
     * if not -> show dialog with error description for user.
     */
    private fun initFirebaseAuthUI() {
        val apiInstance = GoogleApiAvailability.getInstance()
        val authStatusCode = apiInstance.isGooglePlayServicesAvailable(context)
        logMessage("initFirebaseAuthUI isGooglePlayServicesAvailable = $authStatusCode")
        logMessage(
            "initFirebaseAuthUI isGooglePlayServicesAvailable = ${apiInstance.getErrorString(
                authStatusCode
            )}"
        )
        if (authStatusCode == ConnectionResult.SUCCESS) {
            showFirebaseAuthUIDelayed()
        } else {
            showAuthErrorDialog(apiInstance, authStatusCode)
        }
    }

    private fun showFirebaseAuthUIDelayed() {
        clearAuthHandler()
        handler = Handler(activity?.mainLooper)
        handler?.postDelayed({ showFirebaseAuthUI() }, AUTH_HANDLER_DELAY)
    }

    private fun showFirebaseAuthUI() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.ic_noun_gift_list)
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    private fun showAuthErrorDialog(
        apiInstance: GoogleApiAvailability,
        authStatusCode: Int
    ) {
        activity?.runOnUiThread {
            apiInstance.showErrorDialogFragment(activity, authStatusCode, RC_ERROR_DIALOG) {
                initFirebaseAuthUI()
            }
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
                initFirebaseAuthUI()
                logMessage("onActivityResult has error")
            }
        }

        if (requestCode == RC_ERROR_DIALOG) {
            initFirebaseAuthUI()
        }
    }

    private fun clearAuthHandler() {
        handler?.removeCallbacksAndMessages(null)
        handler = null
    }

    private fun stopTimer() {
        timer?.cancel()
        timer?.purge()
        timer = null
    }

    companion object {
        private const val SPLASH_DELAY = 1000L
        private const val AUTH_HANDLER_DELAY = 200L
        private const val RC_SIGN_IN = 101
        private const val RC_ERROR_DIALOG = 102
    }
}
