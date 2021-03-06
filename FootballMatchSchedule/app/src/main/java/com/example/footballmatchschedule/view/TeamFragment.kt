package com.example.footballmatchschedule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footballmatchschedule.MainActivity
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.model.RetrofitResponse
import com.example.footballmatchschedule.model.apiresponse.League
import com.example.footballmatchschedule.model.apiresponse.Team
import com.example.footballmatchschedule.model.database.TeamDatabase
import com.example.footballmatchschedule.util.helper.FMSHelper
import com.example.footballmatchschedule.util.helper.ResponseListener
import com.example.footballmatchschedule.util.recyclerviewadapter.TeamRecyclerViewAdapter
import com.example.footballmatchschedule.viewmodel.TeamViewModel
import kotlinx.android.synthetic.main.team_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TeamFragment : Fragment() {
    lateinit var teamAdapter: TeamRecyclerViewAdapter

    companion object {
        fun newInstance() = TeamFragment()
    }

    private lateinit var viewModel: TeamViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeamViewModel::class.java)

        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.Default) {
                    viewModel.init(
                        FMSHelper.getUserRepository()
                    )

                }
                initListener()
                initRecyclerView()

                withContext(Dispatchers.IO) {
                    viewModel.requestLeagueList(object :
                        ResponseListener {
                        override fun retrofitResponse(retrofitResponse: RetrofitResponse) {
                            responseAction(retrofitResponse)

                        }


                    })

                }

            }

        }

    }

    private fun initListener() {
        spinner_team_fragment_2_1.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    spinnerListener(parent!!.getItemAtPosition(position).toString())
                }


            }

        searchView_team_fragment_1.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                textSubmitListener(query)

                return true

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }


        })

    }

    private fun spinnerListener(leagueName: String) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.IO) {
                    viewModel.requestTeamList(object :
                        ResponseListener {
                        override fun retrofitResponse(retrofitResponse: RetrofitResponse) {
                            teamListHandler(retrofitResponse)

                        }


                    }, leagueName)

                }

            }

        }

    }

    private fun responseAction(retrofitResponse: RetrofitResponse) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                withContext(Dispatchers.Default) {
                    if (retrofitResponse.isSuccess) {
                        val ab = retrofitResponse.responseBody as League
                        val aa = viewModel.addSpinner(
                            ab.leagues,
                            (activity as MainActivity)
                        )

                        withContext(Dispatchers.Main) { spinner_team_fragment_2_1.adapter = aa }

                    } else {
                        withContext(Dispatchers.Main) {
                            (activity as MainActivity).popUp(retrofitResponse.message)
                        }
                    }

                }

                (activity as MainActivity).stopLoading()

            }

        }

    }

    private fun teamListHandler(retrofitResponse: RetrofitResponse) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {

                withContext(Dispatchers.Default) {
                    if (retrofitResponse.isSuccess) {
                        val LMEData =
                            retrofitResponse.responseBody as Team

                        viewModel.initTeamList(LMEData.teams)
                        withContext(Dispatchers.Main) { teamAdapter.notifyDataSetChanged() }

                    } else {
                        withContext(Dispatchers.Main) {
                            (activity as MainActivity)
                                .popUp(retrofitResponse.message)
                        }
                    }

                }

                (activity as MainActivity).stopLoading()

            }

        }

    }

    private fun textSubmitListener(query: String?) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                searchView_team_fragment_1.clearFocus()
                (activity as MainActivity).exitKeyboard()
                viewModel.requestSearchTeam(object :
                    ResponseListener {
                    override fun retrofitResponse(retrofitResponse: RetrofitResponse) {
                        responseSearchListener(retrofitResponse)

                    }


                }, query)

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

        recyclerView_team_fragment_2_2.layoutManager = mLayoutManager
        recyclerView_team_fragment_2_2.itemAnimator = DefaultItemAnimator()
        recyclerView_team_fragment_2_2.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerView_team_fragment_2_2.adapter = teamAdapter

    }

    private fun responseSearchListener(retrofitResponse: RetrofitResponse) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {

                withContext(Dispatchers.Default) {
                    if (retrofitResponse.isSuccess) {
                        if (!FMSHelper.getHasFragmentBackstack(
                                "SearchTeam"
                            )
                        ) {
                            withContext(Dispatchers.Main) {
                                (activity as MainActivity).changeFragment2(
                                    R.id.constraintLayout_team_fragment_2,
                                    SearchTeamFragment()
                                )
                            }
                            FMSHelper.setHasFragmentBackstack(
                                "SearchTeam",
                                true
                            )

                        }

                        val searchTeam =
                            retrofitResponse.responseBody as Team
                        withContext(Dispatchers.Main) {
                            FMSHelper.setSearchTeamList(searchTeam.teams)
                        }

                    } else {
                        withContext(Dispatchers.Main) {
                            (activity as MainActivity).popUp(retrofitResponse.message)
                        }
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
                    FMSHelper.setIsFromAPI(true)
                }

                (activity as MainActivity)
                    .changeFragment2(R.id.frameLayout_activity_main_1, TeamDetailFragment())

                (activity as MainActivity).stopLoading()

            }

        }

    }

}
