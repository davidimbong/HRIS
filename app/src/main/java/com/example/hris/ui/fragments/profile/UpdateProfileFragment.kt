package com.example.hris.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hris.convertToLocalPhone
import com.example.hris.convertToInternationalLandlineNumber
import com.example.hris.convertToInternationalPhoneNumber
import com.example.hris.convertToLocalLandline
import com.example.hris.databinding.FragmentUpdateProfileBinding
import com.example.hris.ui.viewmodels.MainActivityViewModel
import com.example.hris.ui.viewmodels.profile.UpdateProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateProfileFragment : Fragment() {

    private lateinit var binding: FragmentUpdateProfileBinding
    private val viewModel: UpdateProfileViewModel by viewModels()
    private val mainViewModel: MainActivityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnUpdate.setOnClickListener {
            viewModel.updateProfile(
                firstName = binding.txtFirstName.text.toString(),
                middleName = binding.txtMiddleName.text.toString(),
                lastName = binding.txtLastName.text.toString(),
                emailAddress = binding.txtEmail.text.toString(),
                mobileNumber = binding.txtMobileNumber.text.toString().convertToLocalPhone(),
                landline = binding.txtLandLine.text.toString().convertToLocalLandline(),
                context = requireContext()
            )
        }

        viewModel.userData.observe(viewLifecycleOwner) {
            binding.apply {
                txtIdNumber.isEnabled = false
                if (!it.middleName.isNullOrEmpty())
                    txtMiddleName.setText(it.middleName)

                if (!it.landline.isNullOrEmpty())
                    txtLandLine.setText(it.landline.convertToInternationalLandlineNumber())

                txtFirstName.setText(it.firstName)
                txtLastName.setText(it.lastName)
                txtIdNumber.setText(it.idNumber)
                txtEmail.setText(it.emailAddress)
                txtMobileNumber.setText(it.mobileNumber.convertToInternationalPhoneNumber())
            }
        }

        viewModel.loadingDialogState.observe(viewLifecycleOwner) {
            mainViewModel.apiBool.value = it
        }

        viewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.isSuccessfull.observe(viewLifecycleOwner) {
            findNavController().navigate(UpdateProfileFragmentDirections.actionUpdateProfileFragmentToUpdateProfileSuccessFragment())
        }
    }
}