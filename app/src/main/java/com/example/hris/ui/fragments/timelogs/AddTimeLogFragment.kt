package com.example.hris.ui.fragments.timelogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hris.R
import com.example.hris.databinding.FragmentAddTimeLogBinding
import com.example.hris.ui.MainActivity
import com.example.hris.ui.viewmodels.AddTimeLogsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTimeLogFragment : Fragment() {
    private val viewModel: AddTimeLogsViewModel by viewModels()
    private lateinit var binding: FragmentAddTimeLogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTimeLogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addTimelogsToolbar.btnDone.setOnClickListener {
            val type = binding.Spinner.selectedItemPosition + 1

            viewModel.addTimeLogs(
                type.toString()
            )
        }

        binding.addTimelogsToolbar.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.action_addTimeLogsFragment_to_timeLogsFragment)
        }

        viewModel.loadingDialogState.observe(viewLifecycleOwner) {
            (activity as MainActivity).setLoadingDialog(it)
        }

        viewModel.callValue.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            if (it.status == "0") {
                val action =
                    AddTimeLogFragmentDirections.actionAddTimeLogsFragmentToAddTimeLogSuccessFragment(
                        binding.Spinner.selectedItem.toString()
                    )
                findNavController().navigate(action)
            }
        }
    }
}