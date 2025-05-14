package com.unity.mindgarden

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unity.mindgarden.diary.DiaryView
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
        val yearFormatter = SimpleDateFormat("yyyy", Locale.getDefault())
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

        holder.singleItemHistory.setOnClickListener {
            val date = dateFormatter.format(items[position].dateTime)
            val month = monthFormatter.format(items[position].dateTime)
            val year = yearFormatter.format(items[position].dateTime)

            val intent = Intent(holder.singleItemHistory.context, DiaryView::class.java)
            intent.putExtra("title", items[position].title)
            intent.putExtra("content", items[position].content)
            intent.putExtra("dateTime", "$month $date, $year")
            intent.putExtra("label", items[position].label)
            intent.putExtra("score", items[position].score)
            intent.putExtra("documentId", items[position].documentId)
            intent.putExtra("userId", items[position].userId)

            holder.singleItemHistory.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class DailyHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val singleItemHistory: View = view.findViewById(R.id.single_item_history)
        val tvLabel: TextView = view.findViewById(R.id.tv_daily_label)
        val tvMonth: TextView = view.findViewById(R.id.tv_daily_month)
        val tvDate: TextView = view.findViewById(R.id.tv_daily_date)
        val tvTime: TextView = view.findViewById(R.id.tv_daily_time)
        val ivLabelEmoticon: ImageView = view.findViewById(R.id.iv_label_emoticon)
    }
}