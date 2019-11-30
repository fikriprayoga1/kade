package com.example.footballmatchschedule.model.retrofitresponse

import com.example.footballmatchschedule.model.apiresponse.LME

data class RequestLME(
    var isSuccess: Boolean,
    var message: String,
    var lme: LME?
)