package com.unity.mindgarden.additional_Setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.unity.mindgarden.R
import com.unity.mindgarden.main_feature.MainActivity

class FaqFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_faq, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup tombol kembali
        val btnBack = view.findViewById<ImageView>(R.id.btn_back)
        btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        setupDropdown(
            view = view,
            headerId = R.id.dropdownHeader1,
            contentId = R.id.dropdownContent,
            iconId = R.id.dropdownIcon1
        )


        // Setup for the rest of the FAQs with headers and icons
        setupDropdown(
            view = view,
            headerId = R.id.dropdownHeader2,
            contentId = R.id.dropdownContent2,
            iconId = R.id.dropdownIcon2
        )

        setupDropdown(
            view = view,
            headerId = R.id.dropdownHeader3,
            contentId = R.id.dropdownContent3,
            iconId = R.id.dropdownIcon3
        )

        setupDropdown(
            view = view,
            headerId = R.id.dropdownHeader4,
            contentId = R.id.dropdownContent4,
            iconId = R.id.dropdownIcon4
        )

        setupDropdown(
            view = view,
            headerId = R.id.dropdownHeader5,
            contentId = R.id.dropdownContent5,
            iconId = R.id.dropdownIcon5
        )

        setupDropdown(
            view = view,
            headerId = R.id.dropdownHeader6,
            contentId = R.id.dropdownContent6,
            iconId = R.id.dropdownIcon6
        )



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
