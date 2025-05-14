package com.unity.mindgarden.main_feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.unity.mindgarden.R
import com.unity.mindgarden.TreeState

class HomeFragment: Fragment() {

    private lateinit var tvHeaderTitle: TextView
    private lateinit var ivSoulGarden: ImageView
    private lateinit var tvStage: TextView
    private lateinit var tvStatus: TextView
    private lateinit var tvMessage: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvHeaderTitle = view.findViewById(R.id.tv_header_title)
        ivSoulGarden = view.findViewById(R.id.iv_soul_garden)
        tvStage = view.findViewById(R.id.tv_stage)
        tvStatus = view.findViewById(R.id.tv_status)
        tvMessage = view.findViewById(R.id.tv_message)

        // get current user name
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val displayName = currentUser.displayName
            tvHeaderTitle.text = "Halo, $displayName!"
        } else {
            tvHeaderTitle.text = "Halo, Pengguna!"
        }

        val treeStates = mapOf(
            1 to TreeState(
                stageName = "Benih",
                thresholds = listOf(0..50, 51..100, 101..150),
                images = listOf(
                    R.drawable.tree_sprout_dead,
                    R.drawable.tree_sprout_wilting,
                    R.drawable.tree_sprout_healthy
                ),
                statusLabels = listOf("Mati", "Layu", "Subur"),
                messages = listOf(
                    "Benihmu belum tumbuh. Tak masalah jika hari ini terasa berat. Coba lagi esok, aku tetap di sini.",
                    "Benihmu butuh sedikit cahaya. Tidak apa-apa merasa lelah. Cobalah ceritakan sedikit lagi, hatimu akan lebih lega.",
                    "Benih harapan mulai tumbuh. Terima kasih telah berani jujur hari ini. Lanjutkan, setiap curahanmu berarti."
                )
            ),
            2 to TreeState(
                stageName = "Tunas",
                thresholds = listOf(0..100, 101..200, 201..300),
                images = listOf(
                    R.drawable.tree_seedling_dead,
                    R.drawable.tree_seedling_wilting,
                    R.drawable.tree_seedling_healthy
                ),
                statusLabels = listOf("Mati", "Layu", "Subur"),
                messages = listOf(
                    "Tunas tak muncul hari ini. Itu tidak membuatmu lemah. Istirahatlah, dan kembali saat siap.",
                    "Tunasnya mulai goyah. Mungkin hatimu sedang bingung. Ayo, beri lagi sedikit ruang untuk dirimu sendiri.",
                    "Tunas kecilmu kuat. Setiap kata yang kau bagi membuatmu lebih ringan. Teruslah menanam makna."
                )
            ),
            3 to TreeState(
                stageName = "Muda",
                thresholds = listOf(0..150, 151..300, 301..450),
                images = listOf(
                    R.drawable.tree_young_dead,
                    R.drawable.tree_young_wilting,
                    R.drawable.tree_young_healthy
                ),
                statusLabels = listOf("Mati", "Layu", "Subur"),
                messages = listOf(
                    "Batangmu roboh hari ini. Tidak apa-apa. Bahkan pohon besar pernah tumbang. Kamu tetap bisa tumbuh lagi.",
                    "Batangnya mulai rapuh. Hati sedang lelah, ya? Yuk, curahkan sedikit lagi, jangan pendam semua sendiri.",
                    "Batangmu tegak. Kamu tumbuh luar biasa. Terus berbagi rasa, karena kamu layak didengar."
                )
            ),
            4 to TreeState(
                stageName = "Tua",
                thresholds = listOf(0..200, 201..400, 401..600),
                images = listOf(
                    R.drawable.tree_near_full_grown_dead,
                    R.drawable.tree_near_full_grown_wilting,
                    R.drawable.tree_near_full_grown_healthy
                ),
                statusLabels = listOf("Mati", "Layu", "Subur"),
                messages = listOf(
                    "Pohonmu kering. Kamu mungkin merasa kosong. Ayo mulai lagi dari hal kecil, aku ada untuk menemani.",
                    "Pohonmu lelah. Bicarakan apa yang mengganggumu, tak perlu menahan semuanya sendiri.",
                    "Pohonmu rindang dan kuat. Kamu telah berjalan jauh. Terima kasih sudah bertahan sejauh ini.",
                )
            ),
            5 to TreeState(
                stageName = "Sempurna",
                thresholds = listOf(0..250, 251..500, 501..751),
                images = listOf(
                    R.drawable.tree_full_grown_dead,
                    R.drawable.tree_full_grown_wilting,
                    R.drawable.tree_full_grown_healthy
                ),
                statusLabels = listOf("Mati", "Layu", "Subur"),
                messages = listOf(
                    "Buah tak jadi tumbuh. Mungkin kamu merasa tak berarti. Tapi benihmu pernah tumbuh, dan bisa tumbuh kembali.",
                    "Bunganya mulai layu. Bahkan saat damai, luka bisa datang. Ayo, jangan diam. Ceritakan agar kamu kembali mekar.",
                    "Pohonmu berbunga indah. Curahan hatimu telah menghidupkanmu. Jadikan hari ini ruang untuk berbagi dan memberi.",
                )
            )
        )

        val db = FirebaseFirestore.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser?.uid // atau uid spesifik

        db.collection("users").document(uid!!)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val soulGarden = document.get("soul_garden") as? Map<*, *>
                    if (soulGarden != null) {
                        val state = (soulGarden["state"] as? Long)?.toInt() ?: 0
                        val status = (soulGarden["status"] as? Long)?.toInt() ?: 0
                        val currentScore = (soulGarden["currentScore"] as? Long)?.toInt() ?: 0

                        treeStates[state]?.let { tree ->
                            ivSoulGarden.setImageResource(tree.images[status - 1])
                            tvStage.text = tree.stageName
                            tvStatus.text = tree.statusLabels[status]
                            "\"${tree.messages[status - 1]}\"".also { tvMessage.text = it }
                        }
                    } else {
                        Toast.makeText(context, "Terjadi kesalahan ketika memuat data, pastikan kamu memiliki koneksi internet yang stabil", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Gagal memuat data: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}