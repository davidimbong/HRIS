package com.example.hris.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hris.R
import com.example.hris.convertDateMonthDay
import com.example.hris.convertToNoDecimalString
import com.example.hris.databinding.LeavesListBinding
import com.example.hris.getNumberOfDaysInBetween
import com.example.hris.model.Leaves
import com.example.hris.ui.viewmodels.leaves.LeavesViewModel

class LeavesListAdapter(
    private val list: List<Leaves>,
    private val viewModel: LeavesViewModel
) : RecyclerView.Adapter<LeavesListAdapter.LeavesViewHolder>() {

    class LeavesViewHolder(private var binding: LeavesListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(leaves: Leaves, viewModel: LeavesViewModel) {
            binding.txtLeaveDate.apply {
                val date: String
                if (leaves.dateTo != null) {
                    date =
                        "${leaves.dateFrom.convertDateMonthDay()} to ${leaves.dateTo.convertDateMonthDay()}"
                } else {
                    date = leaves.dateFrom.convertDateMonthDay()
                }
                text = date
            }

            binding.txtNoOfDays.apply {
                val days: Double = if (leaves.dateTo != null) {
                    leaves.dateFrom.getNumberOfDaysInBetween(leaves.dateTo)
                } else if (leaves.time == "1") {
                    1.0
                } else {
                    0.5
                }

                if (leaves.type == "1") {
                    viewModel.totalVacationLeaves.value = days
                    binding.txtLeaveType.text = context.getString(R.string.vacation_leave)
                } else {
                    viewModel.totalSickLeaves.value = days
                    binding.txtLeaveType.text = context.getString(R.string.sick_leave)
                }

                text = days.convertToNoDecimalString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeavesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LeavesViewHolder(
            LeavesListBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LeavesViewHolder, position: Int) {
        val leave = list[position]
        holder.bind(leave, viewModel)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}