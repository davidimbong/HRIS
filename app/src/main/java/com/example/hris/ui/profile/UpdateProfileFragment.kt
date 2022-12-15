package com.example.hris.ui.profile

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hris.R
import com.example.hris.convertToPhone
import com.example.hris.databinding.FragmentUpdateProfileBinding
import com.example.hris.ui.CustomDialogFragment
import com.example.hris.ui.DialogState
import com.example.hris.ui.FragmentType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UpdateProfileFragment : Fragment() {

    private lateinit var binding: FragmentUpdateProfileBinding
    private val viewModel: UpdateProfileViewModel by viewModels()

    @Inject
    lateinit var loadingDialog: CustomDialogFragment

//    private val loadingDialog: Dialog by lazy {
//        Dialog(requireContext()).apply {
//            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            this.setCancelable(false)
//            this.setContentView(R.layout.api_calling_dialog)
//        }
//    }

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
//            viewModel.updateProfile(
//                binding.txtFirstName.text.toString(),
//                binding.txtMiddleName.text.toString(),
//                binding.txtLastName.text.toString(),
//                binding.txtEmail.text.toString(),
//                binding.txtMobileNumber.text.toString().convertToLocalPhone(),
//                binding.txtLandLine.text.toString()
//            )
            val action =
                UpdateProfileFragmentDirections.actionUpdateProfileFragmentToSuccessFragment(
                    FragmentType.PROFILE
                )
            findNavController().navigate(action)
        }

        viewModel.userData.observe(viewLifecycleOwner) {
            binding.apply {
                txtIdNumber.isEnabled = false

                val phoneNumber = it.mobileNumber.convertToPhone()

                if (!it.middleName.isNullOrEmpty())
                    txtMiddleName.setText(it.middleName)

                if (!it.landline.isNullOrEmpty())
                    txtLandLine.setText(it.landline)

                txtFirstName.setText(it.firstName)
                txtLastName.setText(it.lastName)
                txtIdNumber.setText(it.idNumber)
                txtEmail.setText(it.emailAddress)
                txtMobileNumber.setText(phoneNumber)
            }
        }

        viewModel.loadingDialogState.observe(viewLifecycleOwner){
            loadingDialog.apiCalling(it, childFragmentManager)
        }

        viewModel.message.observe(viewLifecycleOwner){
            loadingDialog.apiToast(it)
        }
    }
}