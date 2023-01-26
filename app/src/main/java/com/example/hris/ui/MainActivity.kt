package com.example.hris.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hris.R
import com.example.hris.databinding.ActivityMainBinding
import com.example.hris.ui.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    private val loadingDialog: Dialog by lazy {
        Dialog(this).apply {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
            this.setCancelable(false)
            this.setContentView(R.layout.api_calling_dialog)
        }
    }

    private val builder: AlertDialog.Builder by lazy {
        AlertDialog.Builder(this).apply {
            this.setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val navBar = binding.bottomNavBar
        navBar.setupWithNavController(navController)

        navBar.setOnItemSelectedListener {
            navController.navigateUp()
            when (it.itemId) {
                R.id.menuMe -> navController.navigate(R.id.profileFragment)
                R.id.menuLeaves -> navController.navigate(R.id.leavesFragment)
                R.id.menuTimeLogs -> navController.navigate(R.id.timeLogsFragment)
                else -> {

                }
            }
            true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.profileFragment -> navBar.menu.getItem(2).isChecked = true
                R.id.leavesFragment -> navBar.menu.getItem(1).isChecked = true
                R.id.timeLogsFragment -> navBar.menu.getItem(0).isChecked = true
            }
        }

        viewModel.apiBool.observe(this) {
            setLoadingDialog(it)
        }

        viewModel.errorMessage.observe(this) {
            setErrorDialog(it)
        }
    }

    private fun setLoadingDialog(isLoading: Boolean) {
        if (isLoading) {
            loadingDialog.show()
        } else {
            loadingDialog.dismiss()
        }
    }

    private fun setErrorDialog(message: String) {
        builder.setMessage(message)
        builder.show()
    }
}