package com.unity.mindgarden

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class FailActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fail)

        val backButton = findViewById<Button>(R.id.btn_fail_back)

        backButton.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}