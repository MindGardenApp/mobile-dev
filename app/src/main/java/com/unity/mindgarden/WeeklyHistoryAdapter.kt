package com.unity.mindgarden

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class WeeklyHistoryAdapter(private val weeklyHistoryList: List<WeeklyHistory>) :
    RecyclerView.Adapter<WeeklyHistoryAdapter.WeeklyHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weekly_history, parent, false)
        return WeeklyHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeeklyHistoryViewHolder, position: Int) {
        val weeklyHistory = weeklyHistoryList[position]

        val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        holder.dateStart.text = formatter.format(weeklyHistory.dateStart)

        holder.dailyRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.dailyRecyclerView.adapter = DailyHistoryAdapter(weeklyHistory.items)
    }

    override fun getItemCount(): Int {
        return weeklyHistoryList.size
    }

    inner class WeeklyHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateStart: TextView = itemView.findViewById(R.id.tv_weekly_date)
        val dailyRecyclerView: RecyclerView = itemView.findViewById(R.id.recycler_daily_history)
    }
}