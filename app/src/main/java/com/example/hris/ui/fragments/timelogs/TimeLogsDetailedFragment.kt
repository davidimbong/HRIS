package com.example.hris.ui.fragments.timelogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.hris.databinding.FragmentTimeLogsDetailedBinding
import com.example.hris.textColorSetter
import com.example.hris.ui.viewmodels.TimeLogsDetailedViewModel

class TimeLogsDetailedFragment : Fragment() {

    private lateinit var binding: FragmentTimeLogsDetailedBinding
    private val viewModel: TimeLogsDetailedViewModel by viewModels()
    private val args by navArgs<TimeLogsDetailedFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimeLogsDetailedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = "Profile"

        val timeLog = args.timeLog

        binding.txtTimeIn.text = viewModel.nullChecker(timeLog.timeIn)
        binding.txtTimeIn.setTextColor(timeLog.timeIn.textColorSetter())

        binding.txtBreakOut.text = viewModel.nullChecker(timeLog.breakOut)
        binding.txtBreakOut.setTextColor(timeLog.breakOut.textColorSetter())

        binding.txtBreakIn.text = viewModel.nullChecker(timeLog.breakIn)
        binding.txtBreakIn.setTextColor(timeLog.breakIn.textColorSetter())

        binding.txtTimeOut.text = viewModel.nullChecker(timeLog.timeOut)
        binding.txtTimeOut.setTextColor(timeLog.timeOut.textColorSetter())

    }
}