package com.example.hris.ui.fragments.timelogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hris.databinding.FragmentTimeLogsBinding
import com.example.hris.ui.adapters.TimeLogsListAdapter
import com.example.hris.ui.fragments.CustomDialogFragment
import com.example.hris.ui.viewmodels.TimeLogsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TimeLogsFragment : Fragment() {
    @Inject
    lateinit var loadingDialog: CustomDialogFragment
    private val viewModel: TimeLogsViewModel by viewModels()
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

        requireActivity().actionBar

        var userID = ""
        binding.timelogsToolbar.btnAdd.setOnClickListener {
            val action =
                TimeLogsFragmentDirections.actionTimeLogsFragmentToAddTimeLogsFragment(userID)
            findNavController().navigate(action)
        }

        viewModel.user.observe(viewLifecycleOwner) {
            userID = it.userID
            viewModel.getTimeLogs(userID)
        }

        viewModel.loadingDialogState.observe(viewLifecycleOwner) {
            loadingDialog.apiCalling(it, childFragmentManager)
        }

        viewModel.message.observe(viewLifecycleOwner) {
            loadingDialog.apiToast(it)
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
    }

}