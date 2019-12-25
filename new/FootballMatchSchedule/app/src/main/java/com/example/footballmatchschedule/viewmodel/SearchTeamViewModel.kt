package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.SearchTeamDetail
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.other.recyclerviewadapter.SearchTeamRecyclerViewAdapter
import com.example.footballmatchschedule.view.MainActivity

class SearchTeamViewModel : ViewModel() {
    // 1
    private lateinit var userRepository: UserRepository
    // 2
    private lateinit var mainActivity: MainActivity
    // 3
    private val searchSearchTeamObjects: MutableList<SearchTeamRecyclerViewAdapter.SearchTeamObject> = ArrayList()
    // 4
    private lateinit var searchSearchTeamObject: SearchTeamRecyclerViewAdapter.SearchTeamObject

    fun init(userRepository: UserRepository, mainActivity: MainActivity) {
        this.userRepository = userRepository
        this.mainActivity = mainActivity

    }

    fun initSearchTeamList(searchSearchTeamList: List<SearchTeamDetail>?) {
        searchSearchTeamObjects.clear()

        if (searchSearchTeamList != null) {
            for (i in searchSearchTeamList.indices) {
                searchSearchTeamObject = SearchTeamRecyclerViewAdapter.SearchTeamObject(searchSearchTeamList[i])
                searchSearchTeamObjects.add(searchSearchTeamObject)

            }

        }

    }

    fun getSearchTeamObjects(): MutableList<SearchTeamRecyclerViewAdapter.SearchTeamObject> {
        return searchSearchTeamObjects
    }

    fun getMainActivity(): MainActivity {
        return mainActivity
    }
}
