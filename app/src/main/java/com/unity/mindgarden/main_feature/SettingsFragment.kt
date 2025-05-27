package com.unity.mindgarden.main_feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.unity.mindgarden.R
import com.unity.mindgarden.additional_Setting.DevFragment
import com.unity.mindgarden.additional_Setting.FaqFragment
import com.unity.mindgarden.additional_Setting.PrivFragment

class SettingsFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Menuju FAQ
        val teksFaq = view.findViewById<View>(R.id.teksfaq)
        val imageFaq = view.findViewById<View>(R.id.imagefaq)

        // Buat satu listener untuk keduanya
        val goToFaqClickListener = View.OnClickListener {
            val faqFragment = FaqFragment()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, faqFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        // Pasang listener ke masing-masing view
        teksFaq.setOnClickListener(goToFaqClickListener)
        imageFaq.setOnClickListener(goToFaqClickListener)


//      Menuju Kontak Developer
        val teksdev = view.findViewById<View>(R.id.tekskontakdeveloper)
        val imageDev = view.findViewById<View>(R.id.imagetelephone)

        // Buat satu listener untuk keduanya
        val goToDevClickListener = View.OnClickListener {
            val devFragment = DevFragment()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, devFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        // Pasang listener ke masing-masing view
        teksdev.setOnClickListener(goToDevClickListener)
        imageDev.setOnClickListener(goToDevClickListener)

        // Menuju Privasi
        val tekspriv = view.findViewById<View>(R.id.teksprivacy)
        val imagepriv = view.findViewById<View>(R.id.imageprivacy)

        // Buat satu listener untuk keduanya
        val goToPrivacyClickListener = View.OnClickListener {
            val privacyFragment = PrivFragment()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, privacyFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        // Pasang listener ke masing-masing view
        tekspriv.setOnClickListener(goToPrivacyClickListener)
        imagepriv.setOnClickListener(goToPrivacyClickListener)
    }

}