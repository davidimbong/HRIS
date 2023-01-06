package com.example.hris.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class TimeLogsModel(
    override val status: String?,
    override val message: String?,
    val timeLogs: List<TimeLogs>?
) : ResponseModel()

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

@JsonClass(generateAdapter = true)
data class AddTimeLogsResponseModel(
    override val status: String?,
    override val message: String?
) : ResponseModel()
