package com.unity.mindgarden

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class Register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.register_page)

        auth = FirebaseAuth.getInstance()

        val etName = findViewById<EditText>(R.id.et_name)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val etConfirm = findViewById<EditText>(R.id.et_confirm_password)
        val btnRegister = findViewById<Button>(R.id.btn_register)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnRegister.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val confirm = etConfirm.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirm) {
                Toast.makeText(this, "Password tidak cocok", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Password minimal 6 karakter", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()
                        user?.updateProfile(profileUpdates)

                        Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, Login::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Gagal: ${task.exception?.message}", Toast.LENGTH_LONG)
                            .show()
                    }
                }
        }

        // Button to Login
        val btnToLogin = findViewById<Button>(R.id.btn_login_page)
        btnToLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}
