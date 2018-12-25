package com.raapp.wishlist.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raapp.wishlist.R
import java.util.*

class SplashActivity : AppCompatActivity() {
    var timer: Timer? = null
    private val delay: Long = 300L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
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
        // TODO add timerTask and start MainActivity after delay
        timer = Timer()
//        timer?.schedule()
        startMainActivity()
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun stopTimer() {
        // TODO stop timer and clear field
    }
}
