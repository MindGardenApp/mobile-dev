package com.unity.mindgarden.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.unity.mindgarden.R

class DiaryViewGenerativeFragment : Fragment() {

    private lateinit var tvGenerativeContent: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history_generative, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvGenerativeContent = view.findViewById(R.id.tv_generative_content)

        arguments?.let {
            val reply = it.getString("reply")

            if (reply != null) {
                tvGenerativeContent.text = reply
            } else {
                tvGenerativeContent.text = "No generative content available."
            }
        }
    }
}