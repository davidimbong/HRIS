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
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hris.R
import com.example.hris.convertDateMonthDayYear
import com.example.hris.databinding.FragmentFileLeaveBinding
import com.example.hris.model.Leaves
import com.example.hris.ui.viewmodels.MainActivityViewModel
import com.example.hris.ui.viewmodels.leaves.FileLeaveViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class FileLeaveFragment : Fragment() {

    private lateinit var binding: FragmentFileLeaveBinding
    private val viewModel: FileLeaveViewModel by viewModels()
    private val mainViewModel: MainActivityViewModel by activityViewModels()
    private var type = ""
    private var startDate: String? = ""
    private var endDate: String? = ""

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
        var leaveType = ""

        val datePickerDialog = createDatePickerDialog()

        binding.vacationLeave.setOnClickListener {
            viewModel.type = "1"
            leaveType = getString(R.string.vacation_leave)
        }

        binding.sickLeave.setOnClickListener {
            viewModel.type = "2"
            leaveType = getString(R.string.sick_leave)
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

        binding.fileLeaveToolbar.btnCancel.setOnClickListener {
            findNavController().navigate(FileLeaveFragmentDirections.actionFileLeaveFragmentToLeavesFragment())
        }

        viewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loadingDialogState.observe(viewLifecycleOwner) {
            mainViewModel.apiBool.value = it
        }

        viewModel.isSuccess.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Leave successfully filed", Toast.LENGTH_SHORT).show()
            val action =
                FileLeaveFragmentDirections.actionFileLeaveFragmentToFileLeaveSuccessFragment(
                    Leaves(
                        type = leaveType,
                        dateFrom = startDate!!,
                        dateTo = endDate,
                        time = binding.Spinner.selectedItem.toString()
                    )
                )
            findNavController().navigate(action)
        }

        binding.Spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
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

    private fun setViewsVisibility(boolean: Boolean) {
        startDate = null
        endDate = null
        binding.apply {
            btnStartDate.text = getString(R.string.select_date)
            btnEndDate.text = getString(R.string.select_date)
            view4.isVisible = boolean
            btnEndDate.isVisible = boolean
            txtEndDate.isVisible = boolean

            if (boolean) {
                txtStartDate.text = getString(R.string.start_date)
            } else {
                txtStartDate.text = getString(R.string.date_)
            }
        }
    }

    fun createDatePickerDialog(): DatePickerDialog {
        val mCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            if (type == getString(R.string.start_date)) {
                startDate = "${month + 1}/$dayOfMonth/$year"
                binding.btnStartDate.text = startDate!!.convertDateMonthDayYear()
            } else {
                endDate = "${month + 1}/$dayOfMonth/$year"
                binding.btnEndDate.text = endDate!!.convertDateMonthDayYear()
            }
        }

        return DatePickerDialog(
            requireContext(),
            datePicker,
            mCalendar.get(Calendar.YEAR),
            mCalendar.get(Calendar.MONTH),
            mCalendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            this.datePicker.minDate = System.currentTimeMillis()
        }
    }
}