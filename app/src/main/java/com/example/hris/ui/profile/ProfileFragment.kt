package com.example.hris.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hris.R
import com.example.hris.convertToPhone
import com.example.hris.databinding.FragmentProfileBinding
import com.example.hris.hideEmail
import com.example.hris.hidePhoneNumber
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userData.observe(viewLifecycleOwner) {
            val userProfile = viewModel.getProfile()
            binding.apply {
                txtName.text = userProfile.name
                txtInitials.text = userProfile.initials
                txtIdNumber.text = userProfile.idNumber
                txtEmail.text = userProfile.emailAddress
                txtMobileNumber.text = userProfile.mobileNumber
            }
        }

        binding.btnUpdate.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_updateProfileFragment)
        }
    }
}