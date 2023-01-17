package com.example.hris.ui.fragments.leaves

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hris.R
import com.example.hris.convertDateMonthDayYear
import com.example.hris.databinding.FragmentFileLeaveBinding
import com.example.hris.ui.viewmodels.MainActivityViewModel
import com.example.hris.ui.viewmodels.leaves.FileLeaveViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class FileLeaveFragment : Fragment() {

    private lateinit var binding: FragmentFileLeaveBinding
    private val viewModel: FileLeaveViewModel by viewModels()
    private val mainViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFileLeaveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var type = ""
        var startDate = ""
        var endDate = ""

        val mCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            if(type == getString(R.string.start_date)){
                startDate = "${month+1}/$dayOfMonth/$year"
                binding.btnStartDate.text = startDate.convertDateMonthDayYear()
            } else {
                endDate = "${month+1}/$dayOfMonth/$year"
                binding.btnEndDate.text = endDate.convertDateMonthDayYear()
            }
        }

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            datePicker,
            mCalendar.get(Calendar.YEAR),
            mCalendar.get(Calendar.MONTH),
            mCalendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            this.datePicker.minDate = System.currentTimeMillis()
        }

        binding.vacationLeave.setOnClickListener {
            viewModel.type = "1"
        }

        binding.sickLeave.setOnClickListener {
            viewModel.type = "2"
        }

        binding.btnStartDate.setOnClickListener {
            type = getString(R.string.start_date)
            datePickerDialog.show()
        }

        binding.btnEndDate.setOnClickListener {
            type = getString(R.string.end_date)
            datePickerDialog.show()
        }

        binding.fileLeaveToolbar.btnDone.setOnClickListener {
            viewModel.fileLeave(
                binding.Spinner.selectedItemPosition + 1,
                startDate,
                endDate
            )
        }

        viewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loadingDialogState.observe(viewLifecycleOwner) {
            mainViewModel.apiBool.value = it
        }

        viewModel.isSuccess.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Leave successfully filed", Toast.LENGTH_SHORT).show()
            findNavController().navigate(FileLeaveFragmentDirections.actionFileLeaveFragmentToLeavesFragment())
        }

        binding.Spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                startDate = ""
                endDate = ""

                if (position == 0) {
                    setViewsVisibility(true)
                } else {
                    setViewsVisibility(false)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    fun setViewsVisibility(boolean: Boolean) {
        binding.btnStartDate.text = getString(R.string.select_date)
        binding.btnEndDate.text = getString(R.string.select_date)
        if (boolean) {
            binding.view4.visibility = View.VISIBLE
            binding.btnEndDate.visibility = View.VISIBLE
            binding.txtEndDate.visibility = View.VISIBLE
            binding.txtStartDate.text = getString(R.string.start_date)
        } else {
            binding.view4.visibility = View.INVISIBLE
            binding.btnEndDate.visibility = View.INVISIBLE
            binding.txtEndDate.visibility = View.INVISIBLE
            binding.txtStartDate.text = getString(R.string.date_)
        }
    }
}