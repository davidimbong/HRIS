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
    private val leaveTypeListener: LeaveTypeListener
) : RecyclerView.Adapter<LeavesListAdapter.LeavesViewHolder>() {

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
        holder.bind(leave, leaveTypeListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

interface LeaveTypeListener {
    fun setTotalLeaves(isVacationLeave: Boolean, days: Double)
}