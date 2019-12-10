package com.example.footballmatchschedule.viewmodel

import android.R
import android.content.Context
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.LeagueDetail
import com.example.footballmatchschedule.other.ResponseListener
import com.example.footballmatchschedule.other.helper.ViewPagerAdapter
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.view.LMEFragment
import com.example.footballmatchschedule.view.MainActivity
import com.example.footballmatchschedule.view.NMEFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class EventViewModel : ViewModel() {
    // 1
    private lateinit var userRepository: UserRepository
    // 2
    private lateinit var mainActivity: MainActivity
    // 4
    private val job = Job()
    // 5
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    // 6
    private val spinnerNameList = ArrayList<String>()
    // 7
    private val spinnerIdList = ArrayList<String>()

    fun init(userRepository: UserRepository, mainActivity: MainActivity) {
        this.userRepository = userRepository
        this.mainActivity = mainActivity

    }

    fun getAdapter(cfm: FragmentManager): ViewPagerAdapter {
        val adapter =
            ViewPagerAdapter(
                cfm,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
            )
        adapter.addFragment(LMEFragment(), "Last Match")
        adapter.addFragment(NMEFragment(), "Next Match")
        return adapter

    }

    fun requestLeagueList(responseListener: ResponseListener) {
        userRepository.requestLeagueList(responseListener)

    }

    fun addSpinner(it: List<LeagueDetail>, context: Context): ArrayAdapter<String> {
        spinnerIdList.clear()
        spinnerNameList.clear()

        for (i in it.indices) {
            spinnerIdList.add(it[i].idLeague!!)
            spinnerNameList.add(it[i].strLeague!!)

        }

        val aa = ArrayAdapter(context, R.layout.simple_spinner_item, spinnerNameList)
        aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        return aa

    }

    fun getLeagueIdList(position: Int): String {
        return spinnerIdList[position]
    }

    fun getJob(): Job {
        return job
    }

    fun getUIScope(): CoroutineScope {
        return uiScope
    }

    fun getMainActivity(): MainActivity {
        return mainActivity
    }

}
