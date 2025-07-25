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
import com.unity.mindgarden.model.ContentRequest
import com.unity.mindgarden.model.CurhatRequest
import com.unity.mindgarden.model.CurhatResponse
import com.unity.mindgarden.model.PredictionResponse
import com.unity.mindgarden.network.RetrofitInstance
import com.unity.mindgarden.utils.calculateStreak
import com.unity.mindgarden.utils.scoreUpdate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DiaryMain : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var etJudulDiary: EditText
    private lateinit var etContentDiary: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_diary_main)

        db = FirebaseFirestore.getInstance()

        etJudulDiary = findViewById(R.id.editText_JudulDiary)
        etContentDiary = findViewById(R.id.editText_Content_Diary)
        btnSubmit = findViewById(R.id.doneButton)
        btnReset = findViewById(R.id.resetButton)

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

                val request = ContentRequest(content)

                RetrofitInstance.api.predictMood(request).enqueue(object : Callback<PredictionResponse> {
                    override fun onResponse(call: Call<PredictionResponse>, response: Response<PredictionResponse>) {
                        if (response.isSuccessful) {
                            val prediction = response.body()
                            processData(userId, title, content, prediction)
                        } else {
                            Toast.makeText(this@DiaryMain, "Prediksi gagal: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                        Toast.makeText(this@DiaryMain, "Koneksi gagal: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "User belum login", Toast.LENGTH_SHORT).show()
            }
        }

        btnReset.setOnClickListener {
            etJudulDiary.text.clear()
            etContentDiary.text.clear()
        }

        val btnBack = findViewById<ImageButton>(R.id.btn_back)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, DiaryWelcome::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }

    private fun processData(userId: String, title: String, content: String, prediction: PredictionResponse?) {
        if (prediction == null) {
            Toast.makeText(this@DiaryMain, "Prediksi tidak valid, silakan coba lagi", Toast.LENGTH_SHORT).show()
            return
        }

        val label = prediction.label
        val score = prediction.score
        val dateFormatted = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())

        scoreUpdate(db, userId, prediction)

        val curhatRequest = CurhatRequest(content)
        RetrofitInstance.api.kirimCurhat(curhatRequest).enqueue(object : Callback<CurhatResponse> {
            override fun onResponse(call: Call<CurhatResponse>, response: Response<CurhatResponse>) {
                val reply = response.body()?.reply ?: content // fallback ke content jika gagal

                val journalData: HashMap<String, Any> = hashMapOf(
                    "title" to title,
                    "content" to content,
                    "dateTime" to Date(),
                    "label" to label,
                    "score" to score,
                    "reply" to reply
                )

                saveToFirestore(userId, journalData, label, title, content, dateFormatted, reply)
            }

            override fun onFailure(call: Call<CurhatResponse>, t: Throwable) {
                val fallbackReply = content // fallback jika gagal memuat balasan AI

                val journalData: HashMap<String, Any> = hashMapOf(
                    "title" to title,
                    "content" to content,
                    "dateTime" to Date(),
                    "label" to label,
                    "score" to score,
                    "reply" to fallbackReply
                )

                saveToFirestore(userId, journalData, label, title, content, dateFormatted, fallbackReply)
            }

        })

        calculateStreak(db, userId, label)
    }

    private fun saveToFirestore(
        userId: String,
        journalData: HashMap<String, Any>,
        label: String,
        title: String,
        content: String,
        dateFormatted: String,
        reply: String
    ) {
        db.collection("users")
            .document(userId)
            .collection("journals")
            .add(journalData)
            .addOnSuccessListener {
                Toast.makeText(this@DiaryMain, "Jurnal berhasil disimpan", Toast.LENGTH_SHORT).show()
                etJudulDiary.text.clear()
                etContentDiary.text.clear()

                val intent = Intent(this@DiaryMain, DiaryResult::class.java).apply {
                    putExtra("label", label)
                    putExtra("title", title)
                    putExtra("content", content)
                    putExtra("date", dateFormatted)
                    putExtra("reply", reply)
                }
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this@DiaryMain, "Gagal menyimpan jurnal: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
