package com.unity.mindgarden.diary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.unity.mindgarden.R
import com.unity.mindgarden.main_feature.MainActivity
import java.util.*

class DiaryMain : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var etJudulDiary: EditText
    private lateinit var etContentDiary: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnReset: Button
    private lateinit var imageButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_diary_main)

        db = FirebaseFirestore.getInstance()

        etJudulDiary = findViewById(R.id.editText_JudulDiary)
        etContentDiary = findViewById(R.id.editText_Content_Diary)
        btnSubmit = findViewById(R.id.doneButton)
        btnReset = findViewById(R.id.resetButton)
        imageButton = findViewById(R.id.imageButton)

        btnSubmit.setOnClickListener {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                val userId = currentUser.uid

                val title = etJudulDiary.text.toString().trim()
                val content = etContentDiary.text.toString().trim()

                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val journalData = hashMapOf(
                    "title" to title,
                    "content" to content,
                    "date" to Date(),
                    "label" to ""
                )

                db.collection("users")
                    .document(userId)
                    .collection("journals")
                    .add(journalData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Jurnal berhasil disimpan", Toast.LENGTH_SHORT).show()
                        etJudulDiary.text.clear()
                        etContentDiary.text.clear()

                        // ✅ Arahkan ke halaman JournalListActivity
                        val intent = Intent(this, DiaryDone::class.java)
                        startActivity(intent)
                        finish() // opsional: supaya tidak bisa kembali ke form dengan tombol back
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Gagal menyimpan jurnal: ${it.message}", Toast.LENGTH_SHORT).show()
                    }

            } else {
                Toast.makeText(this, "User belum login", Toast.LENGTH_SHORT).show()
            }
        }
        btnReset.setOnClickListener {
            etJudulDiary.text.clear()
            etContentDiary.text.clear()
        }
        imageButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}