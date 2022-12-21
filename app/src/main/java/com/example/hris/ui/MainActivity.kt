package com.example.hris.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_content_main)

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


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}