package com.example.hris.ui.login

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.hris.R
import com.example.hris.databinding.ActivityLoginBinding
import com.example.hris.ui.CustomDialogFragment
import com.example.hris.ui.DialogState
import com.example.hris.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var loadingDialog: CustomDialogFragment

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
        }

        viewModel.loadingDialogState.observe(this){
            loadingDialog.apiCalling(it, supportFragmentManager)
        }

        viewModel.message.observe(this){
            loadingDialog.apiToast(it)
        }
    }
}