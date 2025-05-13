package com.unity.mindgarden.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.unity.mindgarden.first_state.Login
import com.unity.mindgarden.R

class onboarding1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding1)

        val btnNext = findViewById<Button>(R.id.nextButton)
        val btnSkip = findViewById<Button>(R.id.skipButton)

        btnNext.setOnClickListener {
            val intent = Intent(this, onboarding2::class.java)
            startActivity(intent)
        }

        btnSkip.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish() // agar tidak bisa kembali ke onboarding
        }
    }
}