package com.unity.mindgarden.main_feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.unity.mindgarden.R
import com.unity.mindgarden.TreeState

class HomeFragment: Fragment() {

    private lateinit var iv_soul_garden: ImageView
    private lateinit var tv_stage: TextView
    private lateinit var tv_status: TextView
    private lateinit var tv_message: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iv_soul_garden = view.findViewById(R.id.iv_soul_garden)
        tv_stage = view.findViewById(R.id.tv_stage)
        tv_status = view.findViewById(R.id.tv_status)
        tv_message = view.findViewById(R.id.tv_message)

        val state = 3
        val score = 400

        val messages: Map<Int, Map<Int, String>> = mapOf(
            1 to mapOf(
                3 to "Benih harapan mulai tumbuh. Terima kasih telah berani jujur hari ini. Lanjutkan, setiap curahanmu berarti.",
                2 to "Benihmu butuh sedikit cahaya. Tidak apa-apa merasa lelah. Cobalah ceritakan sedikit lagi, hatimu akan lebih lega.",
                1 to "Benihmu belum tumbuh. Tak masalah jika hari ini terasa berat. Coba lagi esok, aku tetap di sini."
            ),
            2 to mapOf(
                3 to "Tunas kecilmu kuat. Setiap kata yang kau bagi membuatmu lebih ringan. Teruslah menanam makna.",
                2 to "Tunasnya mulai goyah. Mungkin hatimu sedang bingung. Ayo, beri lagi sedikit ruang untuk dirimu sendiri.",
                1 to "Tunas tak muncul hari ini. Itu tidak membuatmu lemah. Istirahatlah, dan kembali saat siap."
            ),
            3 to mapOf(
                3 to "Batangmu tegak. Kamu tumbuh luar biasa. Terus berbagi rasa, karena kamu layak didengar.",
                2 to "Batangnya mulai rapuh. Hati sedang lelah, ya? Yuk, curahkan sedikit lagi, jangan pendam semua sendiri.",
                1 to "Batangmu roboh hari ini. Tidak apa-apa. Bahkan pohon besar pernah tumbang. Kamu tetap bisa tumbuh lagi."
            ),
            4 to mapOf(
                3 to "Pohonmu rindang dan kuat. Kamu telah berjalan jauh. Terima kasih sudah bertahan sejauh ini.",
                2 to "Pohonmu lelah. Bicarakan apa yang mengganggumu, tak perlu menahan semuanya sendiri.",
                1 to "Pohonmu kering. Kamu mungkin merasa kosong. Ayo mulai lagi dari hal kecil, aku ada untuk menemani."
            ),
            5 to mapOf(
                3 to "Pohonmu berbunga indah. Curahan hatimu telah menghidupkanmu. Jadikan hari ini ruang untuk berbagi dan memberi.",
                2 to "Bunganya mulai layu. Bahkan saat damai, luka bisa datang. Ayo, jangan diam. Ceritakan agar kamu kembali mekar.",
                1 to "Buah tak jadi tumbuh. Mungkin kamu merasa tak berarti. Tapi benihmu pernah tumbuh, dan bisa tumbuh kembali."
            )
        )

        val treeStates = mapOf(
            1 to TreeState(
                stageName = "Benih",
                thresholds = listOf(0..50, 51..100, 101..150),
                images = listOf(
                    R.drawable.tree_sprout_dead,
                    R.drawable.tree_sprout_wilting,
                    R.drawable.tree_sprout_healthy
                ),
                statusLabels = listOf("Mati", "Layu", "Subur")
            ),
            2 to TreeState(
                stageName = "Tunas",
                thresholds = listOf(0..100, 101..200, 201..300),
                images = listOf(
                    R.drawable.tree_seedling_dead,
                    R.drawable.tree_seedling_wilting,
                    R.drawable.tree_seedling_healthy
                ),
                statusLabels = listOf("Mati", "Layu", "Subur")
            ),
            3 to TreeState(
                stageName = "Muda",
                thresholds = listOf(0..150, 151..300, 301..450),
                images = listOf(
                    R.drawable.tree_young_dead,
                    R.drawable.tree_young_wilting,
                    R.drawable.tree_young_healthy
                ),
                statusLabels = listOf("Mati", "Layu", "Subur")
            ),
            4 to TreeState(
                stageName = "Tua",
                thresholds = listOf(0..200, 201..400, 401..600),
                images = listOf(
                    R.drawable.tree_near_full_grown_dead,
                    R.drawable.tree_near_full_grown_wilting,
                    R.drawable.tree_near_full_grown_healthy
                ),
                statusLabels = listOf("Mati", "Layu", "Subur")
            ),
            5 to TreeState(
                stageName = "Sempurna",
                thresholds = listOf(0..250, 251..500, 501..751),
                images = listOf(
                    R.drawable.tree_full_grown_dead,
                    R.drawable.tree_full_grown_wilting,
                    R.drawable.tree_full_grown_healthy
                ),
                statusLabels = listOf("Mati", "Layu", "Subur")
            )
        )

        treeStates[state]?.let { tree ->
            for ((index, range) in tree.thresholds.withIndex()) {
                if (score in range) {
                    iv_soul_garden.setImageResource(tree.images[index])
                    tv_stage.text = tree.stageName
                    tv_status.text = tree.statusLabels[index]
                    "\"${messages[state]?.get(index + 1)}\"".also { tv_message.text = it }
                    break
                }
            }
        }
    }
}