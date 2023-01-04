package com.example.hris.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.hris.R
import com.example.hris.databinding.ActivityLoginBinding
import com.example.hris.ui.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private val loadingDialog: Dialog by lazy {
        Dialog(this).apply {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
            this.setCancelable(false)
            this.setContentView(R.layout.api_calling_dialog)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUserID.text.toString()
            val password = binding.txtPassword.text.toString()

            viewModel.userLogin(username, password)
        }

        viewModel.userData.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        viewModel.loadingDialogState.observe(this) {
            setLoadingDialog(it)
        }

        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    fun setLoadingDialog(loading: Boolean) {
        if (loading) {
            loadingDialog.show()
        } else {
            loadingDialog.dismiss()
        }
    }
}