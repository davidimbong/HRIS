package com.example.hris.ui.adapters

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
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

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(timeLogs: TimeLogs) {
            binding.date.text = timeLogs.date.convertDateMonthDay()
            binding.timeIn.apply {
                if (timeLogs.timeIn.isNullOrEmpty()) {
                    text = "N/A"
                    setTextColor(Color.parseColor("#880808"))
                } else {
                    text = timeLogs.timeIn.convertTime24to12()
                    setTextColor(Color.parseColor("#000000"))
                }
            }
            binding.timeOut.apply {
                if (timeLogs.timeOut.isNullOrEmpty()) {
                    text = "N/A"
                    setTextColor(Color.parseColor("#880808"))
                } else {
                    text = timeLogs.timeOut.convertTime24to12()
                    setTextColor(Color.parseColor("#000000"))
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

    @RequiresApi(Build.VERSION_CODES.O)
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