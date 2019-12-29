package com.example.footballmatchschedule.other.helper

import com.example.footballmatchschedule.model.RetrofitResponse

interface ResponseListener {
    fun retrofitResponse(retrofitResponse: RetrofitResponse)
}