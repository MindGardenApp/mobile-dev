package com.unity.mindgarden.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.unity.mindgarden.R

class DiaryViewTextFragment : Fragment() {

    private lateinit var tvDiaryContent: TextView
    private lateinit var tvDiaryTitle: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvDiaryContent = view.findViewById(R.id.tv_diary_content)
        tvDiaryTitle = view.findViewById(R.id.tv_dairy_title)

        arguments?.let {
            val content = it.getString("content")
            val title = it.getString("title")

            if (content != null && title != null) {
                tvDiaryContent.text = content
                tvDiaryTitle.text = title
            } else {
                tvDiaryContent.text = "No generative content available."
                tvDiaryTitle.text = "No title available."
            }
        }
    }
}
