package com.example.footballmatchschedule.viewmodel

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.util.helper.ViewPagerAdapter
import com.example.footballmatchschedule.view.FavoriteEventFragment
import com.example.footballmatchschedule.view.FavoriteTeamFragment

class FavoriteViewModel : ViewModel() {
    fun getAdapter(cfm: FragmentManager): ViewPagerAdapter {
        val adapter =
            ViewPagerAdapter(
                cfm,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
            )
        adapter.addFragment(FavoriteEventFragment(), "Event")
        adapter.addFragment(FavoriteTeamFragment(), "Team")
        return adapter

    }

}
