package com.example.hris.ui.fragments.timelogs

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hris.R
import com.example.hris.databinding.FragmentAddTimelogSuccessBinding
import com.example.hris.ui.viewmodels.AddTimelogSuccessViewModel

class AddTimelogSuccessFragment : Fragment() {

    private lateinit var binding: FragmentAddTimelogSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTimelogSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: AddTimelogSuccessViewModel by viewModels()

        val args by navArgs<AddTimelogSuccessFragmentArgs>()
        binding.txtSuccess.text = getString(R.string.updateProfileSuccess)
        binding.timelogSuccessLayout.isVisible = true
        binding.tvType.text = args.type
        binding.tvTime.text = viewModel.getTime()
        binding.btnOk.setOnClickListener {
            findNavController().navigate(R.id.action_addTimeLogSuccessFragment_to_timeLogsFragment)
        }
    }
}