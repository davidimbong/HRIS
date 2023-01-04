package com.example.hris.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
data class TimeLogsModel(
    val status: String,
    val message: String?,
    val timeLogs: List<TimeLogs>?
)

@Parcelize
@JsonClass(generateAdapter = true)
@Entity(tableName = "time_logs")
data class TimeLogs (
    @PrimaryKey
    val date: String,
    val timeIn: String?,
    val breakOut: String?,
    val breakIn: String?,
    val timeOut: String?
) : Parcelable
