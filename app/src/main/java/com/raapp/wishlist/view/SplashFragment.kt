package com.raapp.wishlist.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.raapp.wishlist.R
import java.util.*
import kotlin.concurrent.schedule


/**
 * A simple [Fragment] subclass.
 *
 */
class SplashFragment : Fragment() {
    private var timer: Timer? = null
    private val splashDelay = 2000L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
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
            this@SplashFragment.findNavController().navigate(R.id.action_splashFragment_to_wishListFragment)
        }
    }

    private fun stopTimer() {
        timer?.cancel()
        timer?.purge()
        timer = null
    }
}
