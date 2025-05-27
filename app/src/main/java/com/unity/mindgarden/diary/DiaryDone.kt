package com.unity.mindgarden.diary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unity.mindgarden.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DiaryDone : AppCompatActivity() {

    private lateinit var doneButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_diary_done)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        doneButton = findViewById(R.id.btn_continue)
        doneButton.setOnClickListener {
            val label = intent.getStringExtra("label") ?: "No Label"
            val title = intent.getStringExtra("title") ?: "No Title"
            val content = intent.getStringExtra("content") ?: "No Content"
            val date = intent.getStringExtra("date") ?: SimpleDateFormat("MM dd, yyyy", Locale.getDefault()).format(Date())

            val intent = Intent(this, DiaryResult::class.java)
            intent.putExtra("label", label)
            intent.putExtra("title", title)
            intent.putExtra("content", content)
            intent.putExtra("date", date)
            startActivity(intent)
            finish()
        }
    }
}