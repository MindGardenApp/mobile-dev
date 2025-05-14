package com.unity.mindgarden.main_feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unity.mindgarden.DailyHistory
import com.unity.mindgarden.DailyHistoryAdapter
import com.unity.mindgarden.R
import java.util.Date

class HistoryFragment : Fragment() {

    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var historyAdapter: DailyHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyRecyclerView = view.findViewById(R.id.recycler_history)
        historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val data = listOf(
            DailyHistory(
                dateTime = Date(2025, 5, 1),
                title = "Day 1",
                diary = "lorem ipsum",
                label = "joy"
            )
        )

        historyAdapter = DailyHistoryAdapter(data)
        historyRecyclerView.adapter = historyAdapter

        val btnBack = view.findViewById<ImageButton>(R.id.btn_back)
        btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()

            (requireActivity() as? MainActivity)?.activateHomeMenu()
        }

    }
}