package com.example.hris.ui.fragments.leaves

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        setViewsVisibility(leave.time)

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

    private fun setViewsVisibility(time: String) {
        if (time == "1") {
            binding.view4.visibility = View.VISIBLE
            binding.EndDate.visibility = View.VISIBLE
            binding.txtEndDate.visibility = View.VISIBLE
            binding.txtStartDate.text = getString(R.string.start_date)
        } else {
            binding.view5.visibility = View.INVISIBLE
            binding.EndDate.visibility = View.INVISIBLE
            binding.txtEndDate.visibility = View.INVISIBLE
            binding.txtStartDate.text = getString(R.string.date_)
        }
    }
}