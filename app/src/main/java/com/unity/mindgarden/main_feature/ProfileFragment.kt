package com.unity.mindgarden.main_feature

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.unity.mindgarden.R
import com.unity.mindgarden.first_state.Login
import com.unity.mindgarden.utils.SessionManager

class ProfileFragment : Fragment() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etName = view.findViewById(R.id.et_name)
        etEmail = view.findViewById(R.id.et_email)

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val name = currentUser.displayName ?: "Pengguna"
            val email = currentUser.email ?: "-"

            etName.setText(name)
            etEmail.setText(email)
        }

        val logoutButton = view.findViewById<Button>(R.id.btn_logout)

        logoutButton.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah kamu yakin ingin logout?")
                .setPositiveButton("Ya") { _, _ ->
                    FirebaseAuth.getInstance().signOut()

                    val sessionManager = SessionManager(requireContext())
                    sessionManager.setLogin(false)

                    val intent = Intent(requireContext(), Login::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }
}