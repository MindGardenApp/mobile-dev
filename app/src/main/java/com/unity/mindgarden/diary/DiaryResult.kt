package com.unity.mindgarden.diary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unity.mindgarden.R
import com.unity.mindgarden.main_feature.MainActivity
import com.unity.mindgarden.model.ContentRequest
import com.unity.mindgarden.model.CurhatRequest
import com.unity.mindgarden.model.CurhatResponse
import com.unity.mindgarden.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiaryResult : AppCompatActivity() {

    private lateinit var ivEmoticon: ImageView
    private lateinit var tvDate: TextView
    private lateinit var tvResultTitle: TextView
    private lateinit var tvDiaryTitle: TextView
    private lateinit var tvDiaryContent: TextView

    private lateinit var tvAdviceTitle: TextView
    private lateinit var tvAdviceContent: TextView

    private lateinit var btnDelete: ImageButton
    private lateinit var btnHome: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_diary_result)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi view
        ivEmoticon = findViewById(R.id.iv_emoticon)
        tvDate = findViewById(R.id.tv_date)
        tvResultTitle = findViewById(R.id.result_title)
        tvDiaryTitle = findViewById(R.id.tv_dairy_title)
        tvDiaryContent = findViewById(R.id.tv_diary_content)
        tvAdviceTitle = findViewById(R.id.tv_advice_title)
        tvAdviceContent = findViewById(R.id.tv_advice_content)
        btnHome = findViewById(R.id.goHomeButton)

        // Ambil data dari intent
        val label = intent.getStringExtra("label") ?: "No Label"
        val title = intent.getStringExtra("title") ?: "No Title"
        val content = intent.getStringExtra("content") ?: "No Content"
        val date = intent.getStringExtra("date") ?: "No Date"

        // Tampilkan data ke view
        tvDate.text = date
        tvDiaryTitle.text = title
        tvDiaryContent.text = content

        // Tampilkan emosi berdasarkan label
        when (label) {
            "joy" -> {
                ivEmoticon.setImageResource(R.drawable.emoticon_joy)
                tvResultTitle.text = getString(R.string.joy_message)
            }
            "sadness" -> {
                ivEmoticon.setImageResource(R.drawable.emoticon_sadness)
                tvResultTitle.text = getString(R.string.sadness_message)
            }
            "love" -> {
                ivEmoticon.setImageResource(R.drawable.emoticon_love)
                tvResultTitle.text = getString(R.string.love_message)
            }
            "fear" -> {
                ivEmoticon.setImageResource(R.drawable.emoticon_fear)
                tvResultTitle.text = getString(R.string.fear_message)
            }
            "anger" -> {
                ivEmoticon.setImageResource(R.drawable.emoticon_anger)
                tvResultTitle.text = getString(R.string.anger_message)
            }
            else -> {
                ivEmoticon.setImageResource(R.drawable.emoticon_surprise)
                tvResultTitle.text = getString(R.string.surprise_message)
            }
        }

        // Kirim curhat ke AI untuk mendapatkan saran
        kirimCurhatKeAI(content)

        // Tombol kembali ke home
        btnHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun kirimCurhatKeAI(pesan: String) {
        val request = CurhatRequest(pesan)

        RetrofitInstance.api.kirimCurhat(request).enqueue(object : Callback<CurhatResponse> {
            override fun onResponse(call: Call<CurhatResponse>, response: Response<CurhatResponse>) {
                if (response.isSuccessful) {
                    val reply = response.body()?.reply ?: "AI tidak memberikan saran."
                    tvAdviceTitle.text = "Saran untuk kamu"
                    tvAdviceContent.text = reply
                } else {
                    tvAdviceTitle.text = "Gagal memuat saran"
                    tvAdviceContent.text = "Kode error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<CurhatResponse>, t: Throwable) {
                tvAdviceTitle.text = "Gagal memuat saran"
                tvAdviceContent.text = "Kesalahan: ${t.message}"
            }
        })
    }
}
