package com.example.footballmatchschedule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footballmatchschedule.MainActivity
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.model.database.TeamDatabase
import com.example.footballmatchschedule.util.helper.FMSHelper
import com.example.footballmatchschedule.util.recyclerviewadapter.TeamRecyclerViewAdapter
import com.example.footballmatchschedule.viewmodel.FavoriteTeamViewModel
import kotlinx.android.synthetic.main.favorite_team_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteTeamFragment : Fragment() {
    private lateinit var teamAdapter: TeamRecyclerViewAdapter

    companion object {
        fun newInstance() = FavoriteTeamFragment()
    }

    private lateinit var viewModel: FavoriteTeamViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_team_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FavoriteTeamViewModel::class.java)

        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.Default) {
                    viewModel.init(
                        FMSHelper.getUserRepository()
                    )

                }
                initRecyclerView()
                viewModel.getFavoriteTeam()?.observe(this@FavoriteTeamFragment, Observer {
                    eventHandler(it)

                })


                (activity as MainActivity).stopLoading()

            }

        }

    }

    private fun initRecyclerView() {
        teamAdapter = TeamRecyclerViewAdapter(
            context!!,
            viewModel.getTeamObjects(),
            object : TeamRecyclerViewAdapter.TeamListener {
                override fun itemDetail(teamDatabase: TeamDatabase) {
                    selectedItemListener(teamDatabase)

                }

            })
        val mLayoutManager = LinearLayoutManager(context)

        recyclerView_fragment_favorite_team_1.layoutManager = mLayoutManager
        recyclerView_fragment_favorite_team_1.itemAnimator = DefaultItemAnimator()
        recyclerView_fragment_favorite_team_1.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerView_fragment_favorite_team_1.adapter = teamAdapter

    }

    private fun eventHandler(teamList: List<TeamDatabase>?) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.Default) {
                    viewModel.initTeamList(teamList)
                    withContext(Dispatchers.Main) {
                        teamAdapter.notifyDataSetChanged()

                    }

                }

                (activity as MainActivity).stopLoading()

            }

        }

    }

    private fun selectedItemListener(teamDatabase: TeamDatabase) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.Default) {
                    FMSHelper.setSelectedTeam(teamDatabase)
                    FMSHelper.setIsFromAPI(false)
                }

                (activity as MainActivity)
                    .changeFragment2(R.id.frameLayout_activity_main_1, TeamDetailFragment())

                (activity as MainActivity).stopLoading()

            }

        }

    }

}
