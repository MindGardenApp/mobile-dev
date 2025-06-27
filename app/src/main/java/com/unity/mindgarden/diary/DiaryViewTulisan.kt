package com.unity.mindgarden.diary

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.unity.mindgarden.R
import com.unity.mindgarden.first_state.Login

class DiaryViewTulisan : AppCompatActivity() {

    private lateinit var tvResultTitle: TextView
    private lateinit var tvDiaryTitle: TextView
    private lateinit var tvDiaryContent: TextView
    private lateinit var tvDate: TextView
    private lateinit var ivEmoticon: ImageView
    private lateinit var btnBack: ImageButton
    private lateinit var ivBackground: ImageView
    private lateinit var btnDelete: ImageButton

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_diary_view_tulisan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvResultTitle = findViewById(R.id.result_title)
        tvDiaryTitle = findViewById(R.id.tv_dairy_title)
        tvDiaryContent = findViewById(R.id.tv_diary_content)
        tvDate = findViewById(R.id.tv_date)
        ivEmoticon = findViewById(R.id.iv_emoticon)
        btnBack = findViewById(R.id.btn_back)
        ivBackground = findViewById(R.id.iv_background)
        btnDelete = findViewById(R.id.btn_delete)

        btnBack.setOnClickListener {
            finish()
        }

        val diaryTitle = intent.getStringExtra("title")
        val diaryContent = intent.getStringExtra("content")
        val diaryDateTime = intent.getStringExtra("dateTime")
        val diaryLabel = intent.getStringExtra("label")
        val diaryDocumentId = intent.getStringExtra("documentId")
        val diaryUserId = intent.getStringExtra("userId")

        btnDelete.setOnClickListener {
            db.collection("users")
                .document(diaryUserId!!)
                .collection("journals")
                .document(diaryDocumentId!!)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Berhasil menghapus diary", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal menghapus diary", Toast.LENGTH_SHORT).show()
                }
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

            tvDiaryTitle.text = diaryTitle
            tvDiaryContent.text = diaryContent

        val btnToHasil = findViewById<android.widget.Button>(R.id.btn_hasil)
        btnToHasil.setOnClickListener {
            val intent = Intent(this, DiaryView::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        }
    }
}