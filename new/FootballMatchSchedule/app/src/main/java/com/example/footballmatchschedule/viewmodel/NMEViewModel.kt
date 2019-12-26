package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import com.example.footballmatchschedule.other.ResponseListener
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.other.recyclerviewadapter.NMERecyclerViewAdapter
import com.example.footballmatchschedule.view.MainActivity

class NMEViewModel : ViewModel() {
    // 1
    private lateinit var userRepository: UserRepository
    // 2
    private lateinit var mainActivity: MainActivity
    // 3
    private val nmeObjects: MutableList<NMERecyclerViewAdapter.NMEObject> = ArrayList()
    // 4
    private lateinit var nmeObject: NMERecyclerViewAdapter.NMEObject

    fun init(userRepository: UserRepository, mainActivity: MainActivity) {
        this.userRepository = userRepository
        this.mainActivity = mainActivity

    }

    fun initEventList(eventList: List<EventDetail>?) {
        nmeObjects.clear()

        if (eventList != null) {
            for (i in eventList.indices) {
                nmeObject = NMERecyclerViewAdapter.NMEObject(eventList[i])
                nmeObjects.add(nmeObject)

            }

        }

    }

    fun requestNMEList(responseListener: ResponseListener, id: String) {
        userRepository.requestNMEList(id, responseListener)

    }

    fun getMainActivity(): MainActivity {
        return mainActivity
    }

    fun getNMEObjects(): MutableList<NMERecyclerViewAdapter.NMEObject> {
        return nmeObjects
    }
}
