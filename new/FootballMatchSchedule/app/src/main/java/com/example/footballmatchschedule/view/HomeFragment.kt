package com.example.footballmatchschedule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.viewmodel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment() {
    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        bottomNavigationView_home_fragment_3.setOnNavigationItemSelectedListener { p0 ->
            var fragment: Fragment = EventFragment()

            when (p0.itemId) {
                R.id.item_bottomnavigation_manu_1 -> fragment = EventFragment()
                R.id.item_bottomnavigation_manu_2 -> fragment = TeamFragment()
                R.id.item_bottomnavigation_manu_3 -> fragment = FavoriteFragment()
                R.id.item_bottomnavigation_manu_4 -> fragment = AlarmFragment()
            }

            (activity as MainActivity).changeFragment0(R.id.frameLayout_fragment_home_2, fragment)

            true
        }
        bottomNavigationView_home_fragment_3.selectedItemId = R.id.item_bottomnavigation_manu_1

    }

}
