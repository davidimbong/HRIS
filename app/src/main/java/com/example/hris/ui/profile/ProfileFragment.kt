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
            binding.apply {
                val name =
                    "${it.firstName.uppercase()} " +
                            if (!it.middleName.isNullOrEmpty()) "${it.middleName.uppercase()} "
                            else "" +
                                    it.lastName.uppercase()

                name.plus("${it.firstName.uppercase()} ")
                if (!it.middleName.isNullOrEmpty())
                    name.plus("${it.middleName.uppercase()} ")
                name.plus(it.lastName.uppercase())

                val initials = "${it.firstName.first()}${it.lastName.first()}"

                val phoneNumber = it.mobileNumber.convertToPhone().hidePhoneNumber()

                val emailAddress = it.emailAddress.hideEmail()

                txtName.text = name
                txtInitials.text = initials
                txtIdNumber.text = it.idNumber
                txtEmail.text = emailAddress
                txtMobileNumber.text = phoneNumber
            }
        }

        binding.btnUpdate.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_updateProfileFragment)
        }
    }
}