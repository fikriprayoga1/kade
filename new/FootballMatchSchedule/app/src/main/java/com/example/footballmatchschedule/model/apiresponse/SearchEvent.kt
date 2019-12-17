package com.example.footballmatchschedule.model.apiresponse

data class SearchEvent(
    var event: List<SearchEventDetail> = listOf()
)