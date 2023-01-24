package com.example.hris.ui.fragments.leaves

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hris.R
import com.example.hris.convertDateMonthDayYear
import com.example.hris.databinding.FragmentFileLeaveSuccessBinding

class FileLeaveSuccessFragment : Fragment() {

    private lateinit var binding: FragmentFileLeaveSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFileLeaveSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args by navArgs<FileLeaveSuccessFragmentArgs>()
        val leave = args.leave
        setViewsVisibility(leave.isMultipleDays())

        binding.apply {
            type.text = leave.type
            time.text = leave.time
            StartDate.text = leave.dateFrom.convertDateMonthDayYear()
            EndDate.text = leave.dateTo?.convertDateMonthDayYear()
        }

        binding.btnOk.setOnClickListener {
            findNavController().navigate(FileLeaveSuccessFragmentDirections.actionFileLeaveSuccessFragmentToLeavesFragment())
        }

    }

    private fun setViewsVisibility(isVisible: Boolean) {
        binding.apply {
            view4.isVisible = isVisible
            EndDate.isVisible = isVisible
            txtEndDate.isVisible = isVisible

            if (isVisible) {
                txtStartDate.text = getString(R.string.start_date)
            } else {
                txtStartDate.text = getString(R.string.date_)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(
                        FileLeaveSuccessFragmentDirections.actionFileLeaveSuccessFragmentToLeavesFragment()
                    )
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }
}