package com.example.hris.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hris.R
import com.example.hris.databinding.FragmentSuccessBinding
import com.example.hris.databinding.FragmentUpdateProfileBinding
import com.example.hris.ui.profile.UpdateProfileViewModel

enum class FragmentType {
    PROFILE, TIMELOG, LEAVES
}

class SuccessFragment : Fragment() {

    private lateinit var binding: FragmentSuccessBinding
    private val args by navArgs<SuccessFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (args.fragmentType){
            FragmentType.PROFILE -> {
                binding.txtSuccess.text = getString(R.string.updateProfileSuccess)
                binding.successDataLayout.isVisible = false
                binding.btnOk.setOnClickListener {
                    findNavController().navigate(R.id.action_successFragment_to_profileFragment)
                }
            }
            FragmentType.TIMELOG -> TODO()
            FragmentType.LEAVES -> TODO()
        }
    }
}