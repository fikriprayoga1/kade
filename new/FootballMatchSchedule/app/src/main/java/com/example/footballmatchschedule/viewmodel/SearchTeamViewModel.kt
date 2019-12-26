package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.TeamDetail
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.other.recyclerviewadapter.SearchTeamRecyclerViewAdapter
import com.example.footballmatchschedule.view.MainActivity

class SearchTeamViewModel : ViewModel() {
    // 1
    private lateinit var userRepository: UserRepository
    // 2
    private lateinit var mainActivity: MainActivity
    // 3
    private val searchTeamObjects: MutableList<SearchTeamRecyclerViewAdapter.SearchTeamObject> = ArrayList()
    // 4
    private lateinit var searchTeamObject: SearchTeamRecyclerViewAdapter.SearchTeamObject

    fun init(userRepository: UserRepository, mainActivity: MainActivity) {
        this.userRepository = userRepository
        this.mainActivity = mainActivity

    }

    fun initSearchTeamList(searchTeamList: List<TeamDetail>?) {
        searchTeamObjects.clear()

        if (searchTeamList != null) {
            for (i in searchTeamList.indices) {
                searchTeamObject = SearchTeamRecyclerViewAdapter.SearchTeamObject(searchTeamList[i])
                searchTeamObjects.add(searchTeamObject)

            }

        }

    }

    fun getSearchTeamObjects(): MutableList<SearchTeamRecyclerViewAdapter.SearchTeamObject> {
        return searchTeamObjects
    }

    fun getMainActivity(): MainActivity {
        return mainActivity
    }
}
