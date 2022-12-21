package com.example.hris.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.hris.databinding.ActivityLoginBinding
import com.example.hris.ui.fragments.CustomDialogFragment
import com.example.hris.ui.viewmodels.LoginViewModel
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
            this.finish()
        }

        viewModel.loadingDialogState.observe(this){
            loadingDialog.apiCalling(it, supportFragmentManager)
        }

        viewModel.message.observe(this){
            loadingDialog.apiToast(it)
        }
    }
}