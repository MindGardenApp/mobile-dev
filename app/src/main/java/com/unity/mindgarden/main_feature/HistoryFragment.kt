package com.unity.mindgarden.main_feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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

        loadData()
    }

    override fun onResume() {
        super.onResume()
        view?.post {
            loadData()
        }
    }

    private fun loadData() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("users")
            .document(userId)
            .collection("journals")
            .orderBy("dateTime", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                journals.clear()
                if (documents.isEmpty) {
                    llNoDiaryPlaceholder.visibility = View.VISIBLE
                    return@addOnSuccessListener
                }
                for (doc in documents) {
                    val journal = DailyHistory(
                        userId = userId,
                        documentId = doc.id,
                        title = doc.get("title") as String,
                        content = doc.get("content") as String,
                        dateTime = (doc.get("dateTime") as Timestamp).toDate(),
                        label = doc.get("label") as String,
                        score = doc.get("score") as Double,
                        reply = doc.get("reply") as String
                    )
                    journals.add(journal)
                }

                historyAdapter.notifyDataSetChanged()
            }
    }
}