package com.example.hris.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequestBody(
    @Json(name = "UserID")
    val userID: String,

    @Json(name = "Password")
    val password: String
)