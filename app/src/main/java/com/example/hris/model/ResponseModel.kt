package com.example.hris.model

open class ResponseModel(
    var status: String? = "",
    var message: String? = "",
){
    val isSuccess: Boolean
        get() = status == "0"
}