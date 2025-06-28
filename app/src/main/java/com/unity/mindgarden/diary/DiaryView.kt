package com.unity.mindgarden.diary

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unity.mindgarden.R

class DiaryView : AppCompatActivity() {

    private lateinit var tvResultTitle: TextView
    private lateinit var tvDiaryTitle: TextView
    private lateinit var tvDiaryContent: TextView
    private lateinit var tvSaranTitle: TextView
    private lateinit var tvSaranAi: TextView
    private lateinit var tvDate: TextView
    private lateinit var btnToTulisan: Button
    private lateinit var ivEmoticon: ImageView
    private lateinit var ivBackground: ImageView
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
        tvResultTitle = findViewById(R.id.result_title)
        tvDiaryTitle = findViewById(R.id.tv_dairy_title)
        tvDiaryContent = findViewById(R.id.tv_diary_content)
        tvSaranTitle = findViewById(R.id.tv_saran_title)
        tvSaranAi = findViewById(R.id.tv_saran_ai)
        tvDate = findViewById(R.id.tv_date)
        btnToTulisan = findViewById(R.id.btn_tulisanmu)
        ivEmoticon = findViewById(R.id.iv_emoticon)
        ivBackground = findViewById(R.id.iv_background)
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

        val diaryTitle = intent.getStringExtra("title")
        val diaryContent = intent.getStringExtra("content")
        val diaryDateTime = intent.getStringExtra("dateTime")
        val diaryLabel = intent.getStringExtra("label")
        val diaryReply = intent.getStringExtra("reply")

        when (diaryLabel) {
            "joy" -> {
                tvResultTitle.text = "Kamu Sedang\n Bahagia Hari Ini"
                ivEmoticon.setImageResource(R.drawable.emoticon_joy)
                ivBackground.setImageResource(R.drawable.bgbot_joy)
            }
            "sadness" -> {
                tvResultTitle.text = "Kamu Sedang\n Sedih Hari Ini"
                ivEmoticon.setImageResource(R.drawable.emoticon_sadness)
                ivBackground.setImageResource(R.drawable.bgbot_sad)
            }
            "anger" -> {
                tvResultTitle.text = "Kamu Sedang\n Murka Hari Ini"
                ivEmoticon.setImageResource(R.drawable.emoticon_anger)
                ivBackground.setImageResource(R.drawable.bgbot_angry)
            }
            "fear" -> {
                tvResultTitle.text = "Kamu Sedang\n Ketakutan Hari Ini"
                ivEmoticon.setImageResource(R.drawable.emoticon_fear)
                ivBackground.setImageResource(R.drawable.bgbot_fear)
            }
            "surprise" -> {
                tvResultTitle.text = "Kamu Sedang\n Terkejut Hari Ini"
                ivEmoticon.setImageResource(R.drawable.emoticon_surprise)
                ivBackground.setImageResource(R.drawable.bgbot_surprise)
            }
            "love" -> {
                tvResultTitle.text = "Kamu Sedang\n Cinta Hari Ini"
                ivEmoticon.setImageResource(R.drawable.emoticon_love)
                ivBackground.setImageResource(R.drawable.bgbot_love)
            }
        }

        tvDate.text = diaryDateTime

        tvDiaryTitle.text = diaryTitle
        tvDiaryContent.text = diaryContent
        tvSaranAi.text = diaryReply
    }
}
