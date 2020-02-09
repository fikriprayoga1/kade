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
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.model.apiresponse.TeamDetail
import com.example.footballmatchschedule.model.database.TeamDatabase
import com.example.footballmatchschedule.util.recyclerviewadapter.TeamRecyclerViewAdapter
import com.example.footballmatchschedule.viewmodel.SearchTeamViewModel
import kotlinx.android.synthetic.main.search_team_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchTeamFragment : Fragment() {
    lateinit var teamAdapter: TeamRecyclerViewAdapter

    companion object {
        fun newInstance() = SearchTeamFragment()
    }

    private lateinit var viewModel: SearchTeamViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_team_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchTeamViewModel::class.java)

        val thisContext = this
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.Default) {
                    viewModel.init(
                        (activity as MainActivity).viewModel.getUserRepository(),
                        (activity as MainActivity)
                    )

                }
                initRecyclerView()
                (activity as MainActivity).viewModel.getSearchTeamList()
                    ?.observe(thisContext, Observer { searchDataHolderListener(it) })

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

        recyclerView_search_team_fragment_1.layoutManager = mLayoutManager
        recyclerView_search_team_fragment_1.itemAnimator = DefaultItemAnimator()
        recyclerView_search_team_fragment_1.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerView_search_team_fragment_1.adapter = teamAdapter

    }

    private fun searchDataHolderListener(it: List<TeamDetail>?) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.Default) { viewModel.initTeamList(it) }
                teamAdapter.notifyDataSetChanged()

                (activity as MainActivity).stopLoading()

            }

        }

    }

    override fun onDestroy() {
        (activity as MainActivity).viewModel.setHasFragmentBackstack("SearchTeam", false)
        super.onDestroy()
    }

    private fun selectedItemListener(teamDatabase: TeamDatabase) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.Default) {
                    viewModel.getMainActivity().viewModel.setSelectedTeam(teamDatabase)
                    viewModel.getMainActivity().viewModel.setIsFromAPI(true)
                }

                viewModel.getMainActivity()
                    .changeFragment2(R.id.frameLayout_activity_main_1, TeamDetailFragment())

                (activity as MainActivity).stopLoading()

            }

        }

    }

}
