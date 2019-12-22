package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.NMEDetail
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

    fun initNMEList(nmeList: List<NMEDetail>?) {
        nmeObjects.clear()

        if (nmeList != null) {
            for (i in nmeList.indices) {
                nmeObject = NMERecyclerViewAdapter.NMEObject(nmeList[i])
                nmeObjects.add(nmeObject)

            }

        }

    }

    fun requestNMEList(id: String, responseListener: ResponseListener) {
        userRepository.requestNMEList(id, responseListener)

    }

    fun getMainActivity(): MainActivity {
        return mainActivity
    }

    fun getNMEObjects(): MutableList<NMERecyclerViewAdapter.NMEObject> {
        return nmeObjects
    }
}
