package com.unity.mindgarden.first_state

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.unity.mindgarden.R
import com.unity.mindgarden.onboarding.onboarding1

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        fun goToOnboarding1() {
            Intent(this, onboarding1::class.java).also {
                startActivity(it)
                finish()
            }
        }
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_splash_screen)

            Handler(Looper.getMainLooper()).postDelayed({
                goToOnboarding1()
            }, 3000L)
    }
}