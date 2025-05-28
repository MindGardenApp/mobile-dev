package com.unity.mindgarden.first_state

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.unity.mindgarden.R
import com.unity.mindgarden.onboarding.onboarding1
import com.unity.mindgarden.utils.SessionManager
import com.unity.mindgarden.main_feature.MainActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val sessionManager = SessionManager(this)
        val isLoggedIn = sessionManager.isLoggedIn()
        val isOnboardingCompleted = sessionManager.isOnboardingCompleted()

        Handler(Looper.getMainLooper()).postDelayed({
            if (!isOnboardingCompleted) {
                // Jika onboarding belum selesai, ke onboarding1
                startActivity(Intent(this, onboarding1::class.java))
            } else if (isLoggedIn) {
                // Jika sudah login dan onboarding selesai
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // Jika onboarding selesai tapi belum login
                startActivity(Intent(this, Login::class.java))
            }
            finish()
        }, 3000L)
    }
}

