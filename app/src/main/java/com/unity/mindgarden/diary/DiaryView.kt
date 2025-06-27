package com.unity.mindgarden.diary

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unity.mindgarden.R
import com.unity.mindgarden.model.*
import com.unity.mindgarden.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiaryView : AppCompatActivity() {

    private lateinit var tvDiaryTitle: TextView
    private lateinit var tvDiaryContent: TextView
    private lateinit var tvSaranTitle: TextView
    private lateinit var tvSaranAI: TextView
    private lateinit var btnToTulisan: Button
    private lateinit var emoticon: ImageView
    private lateinit var backButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_diary_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Hubungkan komponen XML
        tvDiaryTitle = findViewById(R.id.tv_dairy_title)
        tvDiaryContent = findViewById(R.id.tv_diary_content)
        tvSaranTitle = findViewById(R.id.tv_saran_title)
        tvSaranAI = findViewById(R.id.tv_saran_ai)
        btnToTulisan = findViewById(R.id.btn_tulisanmu)
        emoticon = findViewById(R.id.iv_emoticon)
        backButton = findViewById(R.id.btn_back)

        // Navigasi ke halaman tulisanmu
        btnToTulisan.setOnClickListener {
            val intent = Intent(this, DiaryViewTulisan::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        // Tombol kembali
        backButton.setOnClickListener {
            finish()
        }

        // Ambil isi curhat dari intent
        val isiCurhat = intent.getStringExtra("curhat_user")
        if (!isiCurhat.isNullOrBlank()) {
            kirimKePredict(isiCurhat)
            kirimCurhatKeAI(isiCurhat)
        } else {
            tvDiaryTitle.text = "Tidak ada curhat dikirim."
            tvDiaryContent.text = "-"
            tvSaranTitle.text = ""
            tvSaranAI.text = ""
        }
    }

    private fun kirimKePredict(pesan: String) {
        val request = ContentRequest(pesan)

        RetrofitInstance.api.predictMood(request).enqueue(object : Callback<PredictionResponse> {
            override fun onResponse(call: Call<PredictionResponse>, response: Response<PredictionResponse>) {
                if (response.isSuccessful) {
                    val hasil = response.body()?.label ?: "Emosi tidak dikenali"
                    tvDiaryTitle.text = "Tentang emosimu:"
                    tvDiaryContent.text = hasil
                } else {
                    tvDiaryTitle.text = "Gagal memuat emosi"
                    tvDiaryContent.text = "Kode error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                tvDiaryTitle.text = "Gagal memuat emosi"
                tvDiaryContent.text = "Kesalahan: ${t.message}"
            }
        })
    }


    private fun kirimCurhatKeAI(message: String) {
        val curhat = CurhatRequest(message)

        RetrofitInstance.api.kirimCurhat(curhat).enqueue(object : Callback<CurhatResponse> {
            override fun onResponse(call: Call<CurhatResponse>, response: Response<CurhatResponse>) {
                if (response.isSuccessful) {
                    val reply = response.body()?.reply ?: "Tidak ada balasan dari AI."
                    tvSaranTitle.text = "Saran untuk kamu:"
                    tvSaranAI.text = reply
                } else {
                    tvSaranTitle.text = "Gagal memuat saran"
                    tvSaranAI.text = "Kode error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<CurhatResponse>, t: Throwable) {
                tvSaranTitle.text = "Gagal memuat saran"
                tvSaranAI.text = "Kesalahan: ${t.message}"
            }
        })
    }
}
