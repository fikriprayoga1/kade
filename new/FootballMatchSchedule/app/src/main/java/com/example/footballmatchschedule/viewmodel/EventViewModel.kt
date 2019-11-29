package com.example.footballmatchschedule.viewmodel

import android.R
import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.database.League
import com.example.footballmatchschedule.other.callback.Event
import com.example.footballmatchschedule.other.helper.Tag
import com.example.footballmatchschedule.other.helper.ViewPagerAdapter
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.view.LastMatchEventFragment
import com.example.footballmatchschedule.view.MainActivity
import com.example.footballmatchschedule.view.NextMatchEventFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class EventViewModel : ViewModel() {
    // 1
    private lateinit var userRepository: UserRepository
    // 2
    private var leagueDatabase: LiveData<List<League>>? = null
    // 4
    private val job = Job()
    // 5
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    fun init(userRepository: UserRepository) {
        this.userRepository = userRepository

        if (leagueDatabase == null) {
            leagueDatabase = userRepository.getDatabase().getLeagueList()

        }

    }

    fun getLeagueList(): LiveData<List<League>>? { return leagueDatabase }

    fun getAdapter(cfm: FragmentManager): ViewPagerAdapter {
        val adapter =
            ViewPagerAdapter(
                cfm,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
            )
        adapter.addFragment(LastMatchEventFragment(), "Last Match")
        adapter.addFragment(NextMatchEventFragment(), "Next Match")
        return adapter

    }

    fun requestLeagueList(mainActivity: MainActivity, requestLeagueListListener: Event) {
        userRepository.requestLeagueList(mainActivity, requestLeagueListListener)

    }

    fun addSpinner(it: List<League>, context: Context): ArrayAdapter<String> {
        val spinnerNameList = ArrayList<String>()
        val spinnerIdList = ArrayList<Int>()

        for (i in it.indices) {
            Log.d(Tag().tag, "EventViewModel/60 : $i")
            spinnerIdList.add(it[i].idLeague)
            spinnerNameList.add(it[i].strLeague)

        }

        val aa = ArrayAdapter(context, R.layout.simple_spinner_item, spinnerNameList)
        aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        return aa

    }

    fun getJob(): Job { return job }
    fun getUIScope(): CoroutineScope { return uiScope }

}
