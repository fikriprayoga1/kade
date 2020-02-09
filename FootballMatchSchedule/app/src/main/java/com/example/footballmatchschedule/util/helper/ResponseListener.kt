package com.example.footballmatchschedule.util.helper

import com.example.footballmatchschedule.model.RetrofitResponse

interface ResponseListener {
    fun retrofitResponse(retrofitResponse: RetrofitResponse)
}