package com.example.hris.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.hris.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            if (binding.txtUserID.text.toString().equals("david", ignoreCase = true)
                && binding.txtPassword.text.toString() == "Tera1234"
            ) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }

            else {
                Toast.makeText(
                    this@LoginActivity,
                    "Invalid username or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}