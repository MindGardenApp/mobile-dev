package com.unity.mindgarden.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.unity.mindgarden.R
import com.unity.mindgarden.first_state.Login
import com.unity.mindgarden.main_feature.MainActivity
import com.unity.mindgarden.utils.SessionManager

class onboarding3 : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding3)

        sessionManager = SessionManager(this)

        val btnGetStarted = findViewById<Button>(R.id.getStartedButton)

        btnGetStarted.setOnClickListener {
            // Tandai onboarding sudah selesai
            sessionManager.setOnboardingCompleted(true)

            val intent = if (sessionManager.isLoggedIn()) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, Login::class.java)
            }

            startActivity(intent)
            finish() // Supaya tidak bisa kembali ke onboarding
        }
    }
}
