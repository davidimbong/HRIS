package com.example.hris.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileModel(
    val status: String,
    val message: String?,
    val user: User?
)

@JsonClass(generateAdapter = true)
data class UpdateProfileModel(
    val status: String,
    val message: String,
)

@JsonClass(generateAdapter = true)
@Entity(tableName = "profile_data")
data class User constructor(
    @PrimaryKey
    val userID: String,
    val idNumber: String,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val emailAddress: String,
    val mobileNumber: String,
    val landline: String?
)

data class Profile(
    val initials: String,
    val name: String,
    val idNumber: String,
    val emailAddress: String,
    val mobileNumber: String,
)