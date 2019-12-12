package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.NMEDetail
import com.example.footballmatchschedule.other.ResponseListener
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.other.recyclerviewadapter.NMERecyclerViewAdapter
import com.example.footballmatchschedule.view.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class NMEViewModel : ViewModel() {
    // 1
    private lateinit var userRepository: UserRepository
    // 2
    private lateinit var mainActivity: MainActivity
    // 4
    private val job = Job()
    // 5
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    // 6
    private val nmeObjects: MutableList<NMERecyclerViewAdapter.NMEObject> = ArrayList()
    // 7
    private lateinit var nmeObject: NMERecyclerViewAdapter.NMEObject

    fun init(userRepository: UserRepository, mainActivity: MainActivity) {
        this.userRepository = userRepository
        this.mainActivity = mainActivity

    }

    fun getJob(): Job {
        return job
    }

    fun getUIScope(): CoroutineScope {
        return uiScope
    }

    fun initNMEList(nmeList: List<NMEDetail>) {
        nmeObjects.clear()

        for (i in nmeList.indices) {
            nmeObject = NMERecyclerViewAdapter.NMEObject(nmeList[i])
            nmeObjects.add(nmeObject)

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
