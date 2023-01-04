package com.example.hris.ui

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.hris.R
import com.example.hris.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

enum class DialogState {
    SHOW, HIDE
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    private val loadingDialog: Dialog by lazy {
        Dialog(this).apply {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
            this.setCancelable(false)
            this.setContentView(R.layout.api_calling_dialog)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment_content_main)

        binding.bottomNavBar.setOnItemSelectedListener {
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
    }

    fun setLoadingDialog(loading: Boolean) {
        if (loading) {
            loadingDialog.show()
        } else {
            loadingDialog.dismiss()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}