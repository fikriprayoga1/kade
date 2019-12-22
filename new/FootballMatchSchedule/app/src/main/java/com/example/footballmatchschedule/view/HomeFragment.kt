package com.example.footballmatchschedule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


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

        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                val loadingStatus0 =
                    withContext(Dispatchers.Default) {
                        (activity as MainActivity).viewModel.updateLoading(
                            true
                        )
                    }
                (activity as MainActivity).updateLoading(loadingStatus0, "HomeFragment/44 : start")



                val loadingStatus1 = withContext(Dispatchers.Default) {
                    (activity as MainActivity).viewModel.updateLoading(false)
                }
                (activity as MainActivity).updateLoading(loadingStatus1, "HomeFragment/51 : stop")

            }

        }

        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                val loadingStatus0 =
                    withContext(Dispatchers.Default) {
                        (activity as MainActivity).viewModel.updateLoading(
                            true
                        )
                    }
                (activity as MainActivity).updateLoading(loadingStatus0, "HomeFragment/65 : start")

                initScreen()

                val loadingStatus1 = withContext(Dispatchers.Default) {
                    (activity as MainActivity).viewModel.updateLoading(false)
                }
                (activity as MainActivity).updateLoading(loadingStatus1, "HomeFragment/72 : stop")

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
