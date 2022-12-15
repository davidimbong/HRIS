package com.example.hris.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.hris.R

class CustomDialogFragment: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.api_calling_dialog, container, false)
    }

    fun apiCalling(state: DialogState, manager: FragmentManager) {
        when (state) {
            DialogState.SHOW -> {
                this.show(manager, "")
            }

            DialogState.HIDE -> {
                this.dismiss()
            }
        }
    }

    fun apiToast(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}