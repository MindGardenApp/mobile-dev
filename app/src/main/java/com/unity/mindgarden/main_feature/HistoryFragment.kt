package com.unity.mindgarden

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Date

class HistoryFragment: Fragment() {

    private lateinit var weeklyRecyclerView: RecyclerView
    private lateinit var weeklyHistoryAdapter: WeeklyHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weeklyRecyclerView = view.findViewById(R.id.recycler_weekly_history)
        weeklyRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val data = listOf(
            WeeklyHistory(
                dateStart = Date(2025, 5, 1),
                items = listOf(
                    DailyHistory(
                        dateTime = Date(2025, 5, 1),
                        title = "Day 1",
                        diary = "lorem ipsum",
                        label = "joy"
                    )
                )
            )
        )

        weeklyHistoryAdapter = WeeklyHistoryAdapter(data)
        weeklyRecyclerView.adapter = weeklyHistoryAdapter
    }
}