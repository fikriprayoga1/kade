package com.example.footballmatchschedule.model

data class RetrofitResponse(
    var isSuccess: Boolean,
    var message: String,
    var responseBody: Any?
)