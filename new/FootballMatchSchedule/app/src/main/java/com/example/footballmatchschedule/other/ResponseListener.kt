package com.example.footballmatchschedule.other

import com.example.footballmatchschedule.model.RetrofitResponse

interface ResponseListener {
    fun retrofitResponse(retrofitResponse: RetrofitResponse)
}