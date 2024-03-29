package com.example.hris.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hris.convertDateMonthDay
import com.example.hris.getNumberOfDaysInBetween
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class LeavesModel(
    val leaves: List<Leaves>
) : ResponseModel()

@Parcelize
@JsonClass(generateAdapter = true)
@Entity(tableName = "leaves")
data class Leaves(
    val type: String,
    @PrimaryKey
    val dateFrom: String,
    val dateTo: String?,
    val time: String
) : Parcelable {
    fun checkDates(): String {
        return if (dateTo != null && dateTo != dateFrom) {
            "${dateFrom.convertDateMonthDay()} to ${dateTo.convertDateMonthDay()}"
        } else {
            dateFrom.convertDateMonthDay()
        }
    }

    fun isVacationLeave(): Boolean {
        return type == "1"
    }

    fun getDaysInBetween(): Double {
        return if (dateTo != null) {
            dateFrom.getNumberOfDaysInBetween(dateTo)
        } else {
            0.5
        }
    }

    fun isMultipleDays(): Boolean {
        return getDaysInBetween() > 1.0
    }
}