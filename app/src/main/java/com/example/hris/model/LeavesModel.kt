package com.example.hris.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LeavesModel (
    val leaves: List<Leaves>
        ): ResponseModel()

@JsonClass(generateAdapter = true)
@Entity(tableName = "leaves")
data class Leaves(
    val type: String,
    @PrimaryKey
    val dateFrom: String,
    val dateTo: String?,
    val time: String
)