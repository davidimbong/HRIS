package com.example.hris.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hris.R
import com.example.hris.convertToNoDecimalString
import com.example.hris.databinding.LeavesListBinding
import com.example.hris.getNumberOfDaysInBetween
import com.example.hris.model.Leaves
import com.example.hris.ui.viewmodels.leaves.LeavesViewModel

class LeavesListAdapter(
    private val list: List<Leaves>,
    private val viewModel: LeavesViewModel
) : RecyclerView.Adapter<LeavesListAdapter.LeavesViewHolder>(), LeaveTypeListener {

    class LeavesViewHolder(private var binding: LeavesListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(leaves: Leaves, callback: LeaveTypeListener) {
            binding.txtLeaveDate.text = leaves.checkDates()

            binding.txtNoOfDays.apply {
                val days: Double = if (leaves.dateTo != null) {
                    leaves.dateFrom.getNumberOfDaysInBetween(leaves.dateTo)
                } else if (leaves.time == "1") {
                    1.0
                } else {
                    0.5
                }

                if (leaves.isVacationLeave()) {
                    binding.txtLeaveType.text = context.getString(R.string.vacation_leave)
                } else {
                    binding.txtLeaveType.text = context.getString(R.string.sick_leave)
                }

                callback.setTotalLeaves(leaves.isVacationLeave(), days)

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
        holder.bind(leave, this)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun setTotalLeaves(isVacationLeave: Boolean, days: Double) {
        if (isVacationLeave) {
            viewModel.totalVacationLeaves.value =
                viewModel.totalVacationLeaves.value!! - days
        } else {
            viewModel.totalSickLeaves.value = viewModel.totalSickLeaves.value!! - days
        }
    }

}

interface LeaveTypeListener {
    fun setTotalLeaves(isVacationLeave: Boolean, days: Double)
}