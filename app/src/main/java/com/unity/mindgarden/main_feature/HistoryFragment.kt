package com.unity.mindgarden.main_feature

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.unity.mindgarden.DailyHistory
import com.unity.mindgarden.DailyHistoryAdapter
import com.unity.mindgarden.R

class HistoryFragment : Fragment() {

    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var historyAdapter: DailyHistoryAdapter
    private lateinit var llNoDiaryPlaceholder: LinearLayout

    private val journals = mutableListOf<DailyHistory>()
    private val db = FirebaseFirestore.getInstance()

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
        llNoDiaryPlaceholder = view.findViewById(R.id.ll_no_diary_placeholder)
        historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        historyAdapter = DailyHistoryAdapter(journals)
        historyRecyclerView.adapter = historyAdapter

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("journals")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                journals.clear()
                if (documents.isEmpty) {
                    llNoDiaryPlaceholder.visibility = View.VISIBLE
                    return@addOnSuccessListener
                }
                for (doc in documents) {
                    Log.e("HistoryFragment", doc.data.toString())
                    val journal = doc.toObject(DailyHistory::class.java)
                    journals.add(journal)
                }
                historyAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Gagal ambil data", Toast.LENGTH_SHORT).show()
            }
    }
}