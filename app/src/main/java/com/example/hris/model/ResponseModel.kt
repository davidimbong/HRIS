package com.example.hris.model

abstract class ResponseModel {
    abstract val status: String?
    abstract val message: String?
}