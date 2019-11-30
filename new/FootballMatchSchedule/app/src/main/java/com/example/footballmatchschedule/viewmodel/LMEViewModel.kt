package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.database.LMEDatabase
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.other.recyclerviewadapter.LMERecyclerViewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class LMEViewModel : ViewModel() {
    // 1
    private lateinit var userRepository: UserRepository
    // 2
    private var lmeDatabase: LiveData<List<LMEDatabase>>? = null
    // 4
    private val job = Job()
    // 5
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    // 6
    private val lmeObjects: MutableList<LMERecyclerViewAdapter.LMEObject> = ArrayList()
    // 7
    private lateinit var lmeObject: LMERecyclerViewAdapter.LMEObject

    fun init(userRepository: UserRepository) {
        this.userRepository = userRepository

        if (lmeDatabase == null) {
            lmeDatabase = userRepository.getDatabase().readLastMatchEventList()

        }

    }

    fun getLMEList(): LiveData<List<LMEDatabase>>? { return lmeDatabase }

    fun getJob(): Job { return job }

    fun getUIScope(): CoroutineScope { return uiScope }

    fun hasCache(): Boolean {
        var status = false
        if (lmeDatabase != null) {
            status = true

        }
        return status

    }

    fun addList(it: String): MutableList<LMERecyclerViewAdapter.LMEObject> {
        val lmeList = userRepository.getDatabase().readLastMatchEventList2(it)

        lmeObjects.clear()

        for (i in lmeList.indices) {
            lmeObject = LMERecyclerViewAdapter.LMEObject(lmeList[i])
            lmeObjects.add(lmeObject)

        }

        return lmeObjects

    }

    fun addList2(it: String, lmeList: List<LMEDatabase>): MutableList<LMERecyclerViewAdapter.LMEObject> {
        lmeObjects.clear()

        for (i in lmeList.indices) {
            if (it == lmeList[i].idLeague) {
                lmeObject = LMERecyclerViewAdapter.LMEObject(lmeList[i])
                lmeObjects.add(lmeObject)

            }

        }

        return lmeObjects

    }

//    fun requestLastMatchEventList()

}
