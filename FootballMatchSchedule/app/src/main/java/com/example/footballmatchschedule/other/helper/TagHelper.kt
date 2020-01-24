package com.example.footballmatchschedule.other.helper

import android.util.Log

class TagHelper {
    fun writeTag(className: String?, line: Int, description: String?) {
        Log.d("football_match_schedule", "$className/${line} : $description")
    }
}