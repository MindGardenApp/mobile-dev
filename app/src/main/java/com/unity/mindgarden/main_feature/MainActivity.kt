package com.unity.mindgarden.main_feature

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unity.mindgarden.HistoryFragment
import com.unity.mindgarden.R
import com.unity.mindgarden.diary.DiaryFragment

class MainActivity : AppCompatActivity() {

    private lateinit var homeButton: LinearLayout
    private lateinit var historyButton: LinearLayout
    private lateinit var diaryButton: LinearLayout
    private lateinit var settingsButton: LinearLayout
    private lateinit var profileButton: LinearLayout

    private lateinit var homeIndicator: ImageView
    private lateinit var historyIndicator: ImageView
    private lateinit var settingsIndicator: ImageView
    private lateinit var profileIndicator: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()

        homeButton = findViewById(R.id.btn_home)
        historyButton = findViewById(R.id.btn_history)
        diaryButton = findViewById(R.id.btn_diary)
        settingsButton = findViewById(R.id.btn_settings)
        profileButton = findViewById(R.id.btn_profile)

        homeIndicator = findViewById(R.id.home_menu_indicator)
        historyIndicator = findViewById(R.id.history_menu_indicator)
        settingsIndicator = findViewById(R.id.settings_menu_indicator)
        profileIndicator = findViewById(R.id.profile_menu_indicator)

        homeButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
            clearIndicators()
            homeIndicator.visibility = ImageView.VISIBLE
        }

        historyButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HistoryFragment())
                .commit()
            clearIndicators()
            historyIndicator.visibility = ImageView.VISIBLE
        }

        diaryButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DiaryFragment())
                .commit()
            clearIndicators()
        }

        settingsButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment())
                .commit()
            clearIndicators()
            settingsIndicator.visibility = ImageView.VISIBLE
        }

        profileButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfileFragment())
                .commit()
            clearIndicators()
            profileIndicator.visibility = ImageView.VISIBLE
        }
    }

    private fun clearIndicators() {
        homeIndicator.visibility = ImageView.INVISIBLE
        historyIndicator.visibility = ImageView.INVISIBLE
        settingsIndicator.visibility = ImageView.INVISIBLE
        profileIndicator.visibility = ImageView.INVISIBLE
    }
}
