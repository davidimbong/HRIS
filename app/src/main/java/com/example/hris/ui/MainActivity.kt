package com.example.hris.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hris.R
import dagger.hilt.android.AndroidEntryPoint

enum class DialogState {
    SHOW, HIDE, ERROR
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}