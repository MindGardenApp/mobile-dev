package com.unity.mindgarden.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.unity.mindgarden.first_state.Login
import com.unity.mindgarden.R
import com.unity.mindgarden.utils.SessionManager

class onboarding1 : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding1)

        sessionManager = SessionManager(this)

        val btnNext = findViewById<Button>(R.id.nextButton)
        val btnSkip = findViewById<Button>(R.id.skipButton)

        // Tombol Next, menuju ke onboarding2
        btnNext.setOnClickListener {
            val intent = Intent(this, onboarding2::class.java)
            startActivity(intent)
        }

        // Tombol Skip, simpan status onboarding selesai dan arahkan ke Login
        btnSkip.setOnClickListener {
            // Tandai onboarding sudah selesai
            sessionManager.setOnboardingCompleted(true)

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish() // Agar tidak bisa kembali ke onboarding
        }
    }
}
