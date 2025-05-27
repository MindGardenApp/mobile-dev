package com.unity.mindgarden.main_feature

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.unity.mindgarden.R
import com.unity.mindgarden.TreeState
import com.unity.mindgarden.first_state.Login
import java.util.Calendar


class HomeFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid

    private lateinit var tvHeaderTitle: TextView
    private lateinit var ivSoulGarden: ImageView

    private lateinit var tvStreakSunDate: TextView
    private lateinit var tvStreakMonDate: TextView
    private lateinit var tvStreakTueDate: TextView
    private lateinit var tvStreakWedDate: TextView
    private lateinit var tvStreakThuDate: TextView
    private lateinit var tvStreakFriDate: TextView
    private lateinit var tvStreakSatDate: TextView

    private lateinit var ivStreakSunCheck: ImageView
    private lateinit var ivStreakMonCheck: ImageView
    private lateinit var ivStreakTueCheck: ImageView
    private lateinit var ivStreakWedCheck: ImageView
    private lateinit var ivStreakThuCheck: ImageView
    private lateinit var ivStreakFriCheck: ImageView
    private lateinit var ivStreakSatCheck: ImageView

    private lateinit var ivEmo1: ImageView
    private lateinit var ivEmo2: ImageView
    private lateinit var ivEmo3: ImageView

    private lateinit var progressBarEmo1: ProgressBar
    private lateinit var progressBarEmo2: ProgressBar
    private lateinit var progressBarEmo3: ProgressBar

    private lateinit var tvEmo1Value: TextView
    private lateinit var tvEmo2Value: TextView
    private lateinit var tvEmo3Value: TextView

    private lateinit var tvStreakCount: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            val intent = Intent(requireContext(), Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
            return
        }

        tvHeaderTitle = view.findViewById(R.id.tv_header_title)
        ivSoulGarden = view.findViewById(R.id.iv_soul_garden)

        ivStreakSunCheck = view.findViewById(R.id.iv_streak_sun_check)
        ivStreakMonCheck = view.findViewById(R.id.iv_streak_mon_check)
        ivStreakTueCheck = view.findViewById(R.id.iv_streak_tue_check)
        ivStreakWedCheck = view.findViewById(R.id.iv_streak_wed_check)
        ivStreakThuCheck = view.findViewById(R.id.iv_streak_thu_check)
        ivStreakFriCheck = view.findViewById(R.id.iv_streak_fri_check)
        ivStreakSatCheck = view.findViewById(R.id.iv_streak_sat_check)

        tvStreakCount = view.findViewById(R.id.tv_streak_count)

        setupStreak()

        val displayName = currentUser.displayName ?: "Pengguna"
        tvHeaderTitle.text = "Halo, $displayName!"

        val treeStates = mapOf(
            1 to TreeState(
                thresholds = listOf(0..50, 51..100, 101..150),
                images = listOf(
                    R.drawable.tree_sprout_dead,
                    R.drawable.tree_sprout_wilting,
                    R.drawable.tree_sprout_healthy
                )
            ),
            2 to TreeState(
                thresholds = listOf(0..100, 101..200, 201..300),
                images = listOf(
                    R.drawable.tree_seedling_dead,
                    R.drawable.tree_seedling_wilting,
                    R.drawable.tree_seedling_healthy
                )
            ),
            3 to TreeState(
                thresholds = listOf(0..150, 151..300, 301..450),
                images = listOf(
                    R.drawable.tree_young_dead,
                    R.drawable.tree_young_wilting,
                    R.drawable.tree_young_healthy
                )
            ),
            4 to TreeState(
                thresholds = listOf(0..200, 201..400, 401..600),
                images = listOf(
                    R.drawable.tree_near_full_grown_dead,
                    R.drawable.tree_near_full_grown_wilting,
                    R.drawable.tree_near_full_grown_healthy
                )
            ),
            5 to TreeState(
                thresholds = listOf(0..250, 251..500, 501..751),
                images = listOf(
                    R.drawable.tree_full_grown_dead,
                    R.drawable.tree_full_grown_wilting,
                    R.drawable.tree_full_grown_healthy
                )
            )
        )

        db.collection("users").document(uid!!)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Fetch data gambar
                    val soulGarden = document.get("soul_garden") as? Map<*, *>
                    if (soulGarden != null) {
                        val state = (soulGarden["state"] as? Long)?.toInt() ?: 0
                        val status = (soulGarden["status"] as? Long)?.toInt() ?: 0

                        treeStates[state]?.let { tree ->
                            ivSoulGarden.setImageResource(tree.images[status - 1])
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Terjadi kesalahan ketika memuat data, pastikan kamu memiliki koneksi internet yang stabil",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    // Fetch data streak
                    val streak = document.get("streak") as? Map<*, *>
                    if (streak != null) {
                        val today = Calendar.getInstance()
                        val streakCount = (streak["count"] as? Long)?.toInt() ?: 0
                        val lastStreakDate = streak["last"] as? Timestamp

                        val weekly = streak["weekly"] as? Map<*, *>

                        if (weekly != null) {
                            val streakDays = listOf(
                                Pair(ivStreakSunCheck, weekly["sunday"] as? Boolean ?: false),
                                Pair(ivStreakMonCheck, weekly["monday"] as? Boolean ?: false),
                                Pair(ivStreakTueCheck, weekly["tuesday"] as? Boolean ?: false),
                                Pair(ivStreakWedCheck, weekly["wednesday"] as? Boolean ?: false),
                                Pair(ivStreakThuCheck, weekly["thursday"] as? Boolean ?: false),
                                Pair(ivStreakFriCheck, weekly["friday"] as? Boolean ?: false),
                                Pair(ivStreakSatCheck, weekly["saturday"] as? Boolean ?: false)
                            )

                            streakDays.forEach { (imageView, isChecked) ->
                                if (isChecked) {
                                    imageView.visibility = View.VISIBLE
                                }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Data streak mingguan tidak ditemukan",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        if (lastStreakDate != null) {
                            val lastDate = timestampToCalendar(lastStreakDate)
                            if (
                                lastDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) && (
                                lastDate.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) - 1 ||
                                lastDate.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR))
                            ) {
                                tvStreakCount.text = streakCount.toString()
                            } else {
                                db.collection("user")
                                    .document(uid)
                                    .set(
                                        "streak.count" to 0,
                                    )
                            }
                        }
                    }

                    // Fetch data stats
                    val stats = document.get("stats") as? Map<String, Number>
                    if (stats != null) {
                        ivEmo1 = view.findViewById(R.id.iv_emo_1)
                        ivEmo2 = view.findViewById(R.id.iv_emo_2)
                        ivEmo3 = view.findViewById(R.id.iv_emo_3)

                        progressBarEmo1 = view.findViewById(R.id.progress_bar_emo_1)
                        progressBarEmo2 = view.findViewById(R.id.progress_bar_emo_2)
                        progressBarEmo3 = view.findViewById(R.id.progress_bar_emo_3)

                        tvEmo1Value = view.findViewById(R.id.tv_emo_1_value)
                        tvEmo2Value = view.findViewById(R.id.tv_emo_2_value)
                        tvEmo3Value = view.findViewById(R.id.tv_emo_3_value)

                        val statsData = mutableMapOf(
                            "joy" to 0,
                            "sadness" to 0,
                            "anger" to 0,
                            "fear" to 0,
                            "love" to 0,
                            "surprise" to 0
                        )
                        var totalData = 0

                        for ((key, value) in stats) {
                            totalData += value.toInt()
                            when (key) {
                                "joy" -> statsData["joy"] = value.toInt()
                                "sadness" -> statsData["sadness"] = value.toInt()
                                "anger" -> statsData["anger"] = value.toInt()
                                "fear" -> statsData["fear"] = value.toInt()
                                "love" -> statsData["love"] = value.toInt()
                                "surprise" -> statsData["surprise"] = value.toInt()
                            }
                        }

                        val sortedStats = statsData.toList().sortedByDescending { it.second }

                        for (i in 1 .. 3) {
                            val (emotion, value) = sortedStats.getOrNull(i - 1) ?: continue
                            when (i) {
                                1 -> {
                                    ivEmo1.setImageResource(
                                        when (emotion) {
                                            "joy" -> R.drawable.ic_joy
                                            "sadness" -> R.drawable.ic_sadness
                                            "anger" -> R.drawable.ic_angry
                                            "fear" -> R.drawable.ic_fear
                                            "love" -> R.drawable.ic_love
                                            else -> R.drawable.ic_surprise
                                        }
                                    )
                                    progressBarEmo1.max = totalData
                                    progressBarEmo1.progress = value
                                    if (totalData == 0) {
                                        tvEmo1Value.text = "0%"
                                    } else {
                                        val percentage = (value / totalData * 100).toString() + "%"
                                        tvEmo1Value.text = percentage
                                    }
                                }
                                2 -> {
                                    ivEmo2.setImageResource(
                                        when (emotion) {
                                            "joy" -> R.drawable.ic_joy
                                            "sadness" -> R.drawable.ic_sadness
                                            "anger" -> R.drawable.ic_angry
                                            "fear" -> R.drawable.ic_fear
                                            "love" -> R.drawable.ic_love
                                            else -> R.drawable.ic_surprise
                                        }
                                    )
                                    progressBarEmo2.max = totalData
                                    progressBarEmo2.progress = value
                                    if (totalData == 0) {
                                        tvEmo1Value.text = "0%"
                                    } else {
                                        val percentage = (value / totalData * 100).toString() + "%"
                                        tvEmo1Value.text = percentage
                                    }
                                }
                                3 -> {
                                    ivEmo3.setImageResource(
                                        when (emotion) {
                                            "joy" -> R.drawable.ic_joy
                                            "sadness" -> R.drawable.ic_sadness
                                            "anger" -> R.drawable.ic_angry
                                            "fear" -> R.drawable.ic_fear
                                            "love" -> R.drawable.ic_love
                                            else -> R.drawable.ic_surprise
                                        }
                                    )
                                    progressBarEmo3.max = totalData
                                    progressBarEmo3.progress = value
                                    if (totalData == 0) {
                                        tvEmo1Value.text = "0%"
                                    } else {
                                        val percentage = (value / totalData * 100).toString() + "%"
                                        tvEmo1Value.text = percentage
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(context, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    context,
                    "Gagal memuat data: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun setupStreak() {
        tvStreakSunDate = view?.findViewById(R.id.tv_streak_sun_date) ?: return
        tvStreakMonDate = view?.findViewById(R.id.tv_streak_mon_date) ?: return
        tvStreakTueDate = view?.findViewById(R.id.tv_streak_tue_date) ?: return
        tvStreakWedDate = view?.findViewById(R.id.tv_streak_wed_date) ?: return
        tvStreakThuDate = view?.findViewById(R.id.tv_streak_thu_date) ?: return
        tvStreakFriDate = view?.findViewById(R.id.tv_streak_fri_date) ?: return
        tvStreakSatDate = view?.findViewById(R.id.tv_streak_sat_date) ?: return

        val calendar = Calendar.getInstance()
        val dayOfWeek = Calendar.DAY_OF_WEEK

        calendar.set(dayOfWeek, Calendar.SUNDAY)
        tvStreakSunDate.text = calendar.get(Calendar.DATE).toString()

        calendar.set(dayOfWeek, Calendar.MONDAY)
        tvStreakMonDate.text = calendar.get(Calendar.DATE).toString()

        calendar.set(dayOfWeek, Calendar.TUESDAY)
        tvStreakTueDate.text = calendar.get(Calendar.DATE).toString()

        calendar.set(dayOfWeek, Calendar.WEDNESDAY)
        tvStreakWedDate.text = calendar.get(Calendar.DATE).toString()

        calendar.set(dayOfWeek, Calendar.THURSDAY)
        tvStreakThuDate.text = calendar.get(Calendar.DATE).toString()

        calendar.set(dayOfWeek, Calendar.FRIDAY)
        tvStreakFriDate.text = calendar.get(Calendar.DATE).toString()

        calendar.set(dayOfWeek, Calendar.SATURDAY)
        tvStreakSatDate.text = calendar.get(Calendar.DATE).toString()
    }

    private fun timestampToCalendar(timestamp: Timestamp): Calendar {
        return timestamp.toDate().let { date ->
            Calendar.getInstance().apply {
                time = date
            }
        }
    }
}