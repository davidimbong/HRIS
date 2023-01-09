package com.example.hris.ui.fragments.timelogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hris.R
import com.example.hris.databinding.FragmentTimeLogsBinding
import com.example.hris.ui.adapters.TimeLogsListAdapter
import com.example.hris.ui.viewmodels.MainActivityViewModel
import com.example.hris.ui.viewmodels.TimeLogsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimeLogsFragment : Fragment() {
    private val viewModel: TimeLogsViewModel by viewModels()
    private val mainViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var binding: FragmentTimeLogsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimeLogsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.timelogsToolbar.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_timeLogsFragment_to_addTimeLogsFragment)
        }

        viewModel.user.observe(viewLifecycleOwner) {
            viewModel.callTimeLogs()
        }

        viewModel.loadingDialogState.observe(viewLifecycleOwner) {
            Log.d("ASD", "Time logs")
            mainViewModel.apiBool.value = it
        }

        viewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.timeLogs.observe(viewLifecycleOwner) {
            val adapter = TimeLogsListAdapter(it) { timeLogs ->
                val action =
                    TimeLogsFragmentDirections.actionTimeLogsFragmentToTimeLogsDetailedFragment(
                        timeLogs
                    )
                findNavController().navigate(action)
            }
            binding.timeLogRecyclerView.adapter = adapter
        }

        viewModel.timeLogsResponse.observe(viewLifecycleOwner) {
            viewModel.getTimeLogs()
        }
    }
}