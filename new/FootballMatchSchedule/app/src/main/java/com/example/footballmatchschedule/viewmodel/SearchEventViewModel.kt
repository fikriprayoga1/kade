package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.other.recyclerviewadapter.SearchEventRecyclerViewAdapter
import com.example.footballmatchschedule.view.MainActivity

class SearchEventViewModel : ViewModel() {
    // 1
    private lateinit var userRepository: UserRepository
    // 2
    private lateinit var mainActivity: MainActivity
    // 3
    private val searchEventObjects: MutableList<SearchEventRecyclerViewAdapter.SearchEventObject> =
        ArrayList()
    // 4
    private lateinit var searchEventObject: SearchEventRecyclerViewAdapter.SearchEventObject

    fun init(userRepository: UserRepository, mainActivity: MainActivity) {
        this.userRepository = userRepository
        this.mainActivity = mainActivity

    }

    fun initEventList(eventList: List<EventDetail>?) {
        searchEventObjects.clear()

        if (eventList != null) {
            for (i in eventList.indices) {
                searchEventObject =
                    SearchEventRecyclerViewAdapter.SearchEventObject(eventList[i])
                searchEventObjects.add(searchEventObject)

            }

        }

    }

    fun getSearchEventObjects(): MutableList<SearchEventRecyclerViewAdapter.SearchEventObject> {
        return searchEventObjects
    }

    fun initSearchEventList(it: List<EventDetail>?) {
        searchEventObjects.clear()

        if (it != null) {
            for (i in it.indices) {
                searchEventObject = SearchEventRecyclerViewAdapter.SearchEventObject(it[i])
                searchEventObjects.add(searchEventObject)

            }

        }

    }

    fun getMainActivity(): MainActivity {
        return mainActivity
    }

}
