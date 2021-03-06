package com.example.footballmatchschedule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.footballmatchschedule.MainActivity
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.viewmodel.HomeViewModel
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

        // Template
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                (activity as MainActivity).stopLoading()

            }

        }
        // -----------------

        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                initScreen()

                (activity as MainActivity).stopLoading()

            }

        }

    }

    private fun initScreen() {
        bottomNavigationView_home_fragment_3.setOnNavigationItemSelectedListener { p0 ->
            var fragment: Fragment = EventFragment()

            when (p0.itemId) {
                R.id.item_bottomnavigation_manu_1 -> fragment = EventFragment()
                R.id.item_bottomnavigation_manu_2 -> fragment = TeamFragment()
                R.id.item_bottomnavigation_manu_3 -> fragment = FavoriteFragment()
                R.id.item_bottomnavigation_manu_4 -> fragment = AlarmFragment()
            }

            (activity as MainActivity).changeFragment0(
                R.id.frameLayout_fragment_home_2,
                fragment
            )

            true
        }
        bottomNavigationView_home_fragment_3.selectedItemId =
            R.id.item_bottomnavigation_manu_1

    }

}
