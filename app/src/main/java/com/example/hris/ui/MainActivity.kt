package com.example.hris.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.hris.R
import com.example.hris.databinding.ActivityMainBinding
import com.example.hris.ui.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private val viewModel: MainActivityViewModel by viewModels()

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

        viewModel.apiBool.observe(this){
            Log.d("ASD", "Observe")
            setLoadingDialog(it)
        }
    }

    fun setLoadingDialog(loading: Boolean) {
        Log.d("ASD", "Function")
        if (loading) {
            Log.d("ASD", "Show")
            loadingDialog.show()
        } else {
            Log.d("ASD", "Dismiss")
            loadingDialog.dismiss()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}