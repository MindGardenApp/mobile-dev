package com.unity.mindgarden.additional_Setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.unity.mindgarden.R

class PrivFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_priv, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup dropdown pertama: Data Jurnal Pribadi
        setupDropdown(
            view = view,
            headerId = R.id.dropdownHeader1,
            contentId = R.id.dropdownContent,
            iconId = R.id.dropdownIcon1
        )

        // Setup dropdown kedua: Analisis Emosi oleh AI
        setupDropdown(
            view = view,
            headerId = R.id.dropdownHeader2,
            contentId = R.id.dropdownContent2,
            iconId = R.id.dropdownIcon2
        )

        // Setup dropdown ketiga: Login & Akun
        setupDropdown(
            view = view,
            headerId = R.id.dropdownHeader3,
            contentId = R.id.dropdownContent3,
            iconId = R.id.dropdownIcon3
        )

// Setup dropdown keempat: Hak Kamu
        setupDropdown(
            view = view,
            headerId = R.id.dropdownHeader4,
            contentId = R.id.dropdownContent4,
            iconId = R.id.dropdownIcon4
        )

        // Tombol kembali
        val backButton = view.findViewById<ImageView>(R.id.btn_back)
        backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupDropdown(view: View, headerId: Int, contentId: Int, iconId: Int) {
        val header = view.findViewById<LinearLayout>(headerId)
        val content = view.findViewById<TextView>(contentId)
        val icon = view.findViewById<ImageView>(iconId)

        var isExpanded = false

        header.setOnClickListener {
            isExpanded = !isExpanded
            content.visibility = if (isExpanded) View.VISIBLE else View.GONE
            icon.setImageResource(if (isExpanded) R.drawable.arrow_up else R.drawable.arrow_down)
        }
    }
}
