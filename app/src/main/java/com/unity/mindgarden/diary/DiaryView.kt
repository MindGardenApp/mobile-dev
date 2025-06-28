package com.unity.mindgarden.diary

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unity.mindgarden.R

class DiaryView : AppCompatActivity() {

    private lateinit var tvResultTitle: TextView
    private lateinit var tvDate: TextView
    private lateinit var ivEmoticon: ImageView
    private lateinit var ivBackground: ImageView
    private lateinit var backButton: ImageButton
    private lateinit var btnToTulisan: Button
    private lateinit var btnToGenerative: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_diary_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val diaryTitle = intent.getStringExtra("title")
        val diaryContent = intent.getStringExtra("content")
        val diaryDateTime = intent.getStringExtra("dateTime")
        val diaryLabel = intent.getStringExtra("label")
        val diaryReply = intent.getStringExtra("reply")

        val diaryViewGenerativeFragment = DiaryViewGenerativeFragment()
        diaryViewGenerativeFragment.arguments = Bundle().apply {
            putString("reply", diaryReply)
        }

        val diaryViewTextFragment = DiaryViewTextFragment()
        diaryViewTextFragment.arguments = Bundle().apply {
            putString("title", diaryTitle)
            putString("content", diaryContent)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.diary_result_container, diaryViewGenerativeFragment)
            .commit()

        // Hubungkan komponen XML
        tvResultTitle = findViewById(R.id.result_title)
        tvDate = findViewById(R.id.tv_date)
        ivEmoticon = findViewById(R.id.iv_emoticon)
        ivBackground = findViewById(R.id.iv_background)
        backButton = findViewById(R.id.btn_back)
        btnToTulisan = findViewById(R.id.btn_tulisanmu)
        btnToGenerative = findViewById(R.id.btn_hasil)

        // Navigasi ke halaman tulisanmu
        btnToTulisan.setOnClickListener {
            // Ganti fragment dengan DiaryViewTextFragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.diary_result_container, diaryViewTextFragment)
                .commit()

            // Ganti warna tombol
            btnToTulisan.setBackgroundColor(getColor(R.color.accent_500))
            btnToGenerative.setBackgroundColor(getColor(R.color.accent_300))
        }

        // Navigasi ke halaman hasil generative
        btnToGenerative.setOnClickListener {
            // Ganti fragment dengan DiaryViewGenerativeFragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.diary_result_container, diaryViewGenerativeFragment)
                .commit()

            // Ganti warna tombol
            btnToTulisan.setBackgroundColor(getColor(R.color.accent_300))
            btnToGenerative.setBackgroundColor(getColor(R.color.accent_500))
        }

        // Tombol kembali
        backButton.setOnClickListener {
            finish()
        }

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
    }
}
