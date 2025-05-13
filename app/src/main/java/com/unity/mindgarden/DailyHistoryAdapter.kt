package com.unity.mindgarden

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class DailyHistoryAdapter(private val items: List<DailyHistory>) :
    RecyclerView.Adapter<DailyHistoryAdapter.DailyHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_daily_history, parent, false)
        return DailyHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyHistoryViewHolder, position: Int) {
        val dateFormatter = SimpleDateFormat("dd", Locale.getDefault())
        val monthFormatter = SimpleDateFormat("MMM", Locale.getDefault())
        val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

        when(items[position].label) {
            "joy" -> {
                holder.tvLabel.text = "Senang"
                holder.ivLabelEmoticon.setImageResource(R.drawable.emoticon_joy)
            }
            "sadness" -> {
                holder.tvLabel.text = "Sedih"
                holder.ivLabelEmoticon.setImageResource(R.drawable.emoticon_sadness)
            }
            "anger" -> {
                holder.tvLabel.text = "Marah"
                holder.ivLabelEmoticon.setImageResource(R.drawable.emoticon_anger)
            }
            "fear" -> {
                holder.tvLabel.text = "Takut"
                holder.ivLabelEmoticon.setImageResource(R.drawable.emoticon_fear)
            }
            "surprise" -> {
                holder.tvLabel.text = "Terkejut"
                holder.ivLabelEmoticon.setImageResource(R.drawable.emoticon_surprise)
            }
            "love" -> {
                holder.tvLabel.text = "Cinta"
                holder.ivLabelEmoticon.setImageResource(R.drawable.emoticon_love)
            }
        }

        holder.tvMonth.text = monthFormatter.format(items[position].dateTime)
        holder.tvDate.text = dateFormatter.format(items[position].dateTime)
        holder.tvTime.text = timeFormatter.format(items[position].dateTime)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class DailyHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvLabel: TextView = view.findViewById(R.id.tv_daily_label)
        val tvMonth: TextView = view.findViewById(R.id.tv_daily_month)
        val tvDate: TextView = view.findViewById(R.id.tv_daily_date)
        val tvTime: TextView = view.findViewById(R.id.tv_daily_time)
        val ivLabelEmoticon: ImageView = view.findViewById(R.id.iv_label_emoticon)
    }
}