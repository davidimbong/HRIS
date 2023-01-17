package com.example.hris.ui.fragments.leaves

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hris.R
import com.example.hris.convertToNoDecimalString
import com.example.hris.databinding.FragmentLeavesBinding
import com.example.hris.ui.adapters.LeavesListAdapter
import com.example.hris.ui.viewmodels.MainActivityViewModel
import com.example.hris.ui.viewmodels.leaves.LeavesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeavesFragment : Fragment() {
    private val viewModel: LeavesViewModel by viewModels()
    private val mainViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var binding: FragmentLeavesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeavesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.leavesToolbar.txtTitle.text = getString(R.string.leaves)

        binding.leavesToolbar.btnAdd.setOnClickListener {
            findNavController().navigate(LeavesFragmentDirections.actionLeavesFragmentToFileLeaveFragment())
        }

        viewModel.callLeaves()

        viewModel.loadingDialogState.observe(viewLifecycleOwner) {
            mainViewModel.apiBool.value = it
        }

        viewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.leaves.observe(viewLifecycleOwner) {
            val adapter = LeavesListAdapter(it, viewModel)
            binding.leavesRecyclerView.adapter = adapter
        }

        viewModel.totalVacationLeaves.observe(viewLifecycleOwner) {
            binding.txtVL.text = it.convertToNoDecimalString()
        }

        viewModel.totalSickLeaves.observe(viewLifecycleOwner) {
            binding.txtSL.text = it.convertToNoDecimalString()
        }
    }
}