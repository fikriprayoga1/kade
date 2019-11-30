package com.example.footballmatchschedule.other.callback

import com.example.footballmatchschedule.model.retrofitresponse.RequestLME
import com.example.footballmatchschedule.view.MainActivity

interface RequestLMECallback {
    fun requestLMEList(mainActivity: MainActivity, requestLME: RequestLME)
}