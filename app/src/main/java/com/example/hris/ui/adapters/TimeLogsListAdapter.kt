package com.example.hris.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.hris.R
import com.example.hris.convertDateMonthDay
import com.example.hris.convertTime24to12
import com.example.hris.databinding.TimeLogsListBinding
import com.example.hris.model.TimeLogs

class TimeLogsListAdapter(
    private val list: List<TimeLogs>,
    private val clickListener: (TimeLogs) -> Unit
) : RecyclerView.Adapter<TimeLogsListAdapter.TimeLogsViewHolder>() {

    class TimeLogsViewHolder(private var binding: TimeLogsListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(timeLogs: TimeLogs) {
            binding.date.text = timeLogs.date.convertDateMonthDay()
            binding.timeIn.apply {
                if (timeLogs.timeIn.isNullOrEmpty()) {
                    text = "N/A"
                    setTextColor(ResourcesCompat.getColor(resources, R.color.red, null))
                } else {
                    text = timeLogs.timeIn.convertTime24to12()
                    setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
                }
            }
            binding.timeOut.apply {
                if (timeLogs.timeOut.isNullOrEmpty()) {
                    text = "N/A"
                    setTextColor(ResourcesCompat.getColor(resources, R.color.red, null))
                } else {
                    text = timeLogs.timeOut.convertTime24to12()
                    setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLogsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TimeLogsViewHolder(
            TimeLogsListBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TimeLogsViewHolder, position: Int) {
        val timeLogs = list[position]
        holder.itemView.setOnClickListener {
            clickListener(timeLogs)
        }
        holder.bind(timeLogs)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}