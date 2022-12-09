package com.example.hris.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginModel (
    @Json(name = "Status")
    val status: String,

    @Json(name = "Message")
    val message: String?,

    @Json(name = "User")
    val user: User?
)

@JsonClass(generateAdapter = true)
@Entity(tableName = "user_login")
data class User constructor(
    @PrimaryKey
    @Json(name = "User ID")
    val userID: String,

    @Json(name = "ID number")
    val idNumber: String,

    @Json(name = "First name")
    val firstName: String,

    @Json(name = "Middle name")
    val middleName: String?,

    @Json(name = "Last name")
    val lastName: String,

    @Json(name = "Email address")
    val emailAddress: String,

    @Json(name = "Mobile number")
    val mobileNumber: String,

    @Json(name = "Landline")
    val landline: String?
)