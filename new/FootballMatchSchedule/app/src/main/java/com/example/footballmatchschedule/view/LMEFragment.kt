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
import com.example.footballmatchschedule.model.RetrofitResponse
import com.example.footballmatchschedule.model.SelectedEvent
import com.example.footballmatchschedule.model.apiresponse.LME
import com.example.footballmatchschedule.model.apiresponse.LMEDetail
import com.example.footballmatchschedule.other.ResponseListener
import com.example.footballmatchschedule.other.recyclerviewadapter.LMERecyclerViewAdapter
import com.example.footballmatchschedule.viewmodel.LMEViewModel
import kotlinx.android.synthetic.main.last_match_event_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LMEFragment : Fragment() {
    private lateinit var lmeAdapter: LMERecyclerViewAdapter

    companion object {
        fun newInstance() = LMEFragment()
    }

    private lateinit var viewModel: LMEViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.last_match_event_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LMEViewModel::class.java)

        val thisContext = this
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                val loadingStatus0 =
                    withContext(Dispatchers.Default) {
                        (activity as MainActivity).viewModel.updateLoading(
                            true
                        )
                    }
                (activity as MainActivity).updateLoading(
                    loadingStatus0,
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    "start"
                )

                withContext(Dispatchers.Default) {
                    viewModel.init(
                        (activity as MainActivity).viewModel.getUserRepository(),
                        (activity as MainActivity)
                    )

                }
                initRecyclerView()
                (activity as MainActivity).viewModel.getLeagueIdHolderListener()
                    .observe(thisContext, Observer { leagueIdHolderListener() })

                val loadingStatus1 =
                    withContext(Dispatchers.Default) {
                        (activity as MainActivity).viewModel.updateLoading(
                            false
                        )
                    }
                (activity as MainActivity).updateLoading(
                    loadingStatus1,
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    "stop"
                )

            }

        }

    }

    private fun initRecyclerView() {
        lmeAdapter = LMERecyclerViewAdapter(
            context!!,
            viewModel.getLMEObjects(),
            object : LMERecyclerViewAdapter.LMEListener {
                override fun itemDetail(lmeDetail: LMEDetail) {
                    selectedItemListener(lmeDetail)

                }

            })
        val mLayoutManager = LinearLayoutManager(context)

        recyclerView_last_match_event_fragment_1.layoutManager = mLayoutManager
        recyclerView_last_match_event_fragment_1.itemAnimator = DefaultItemAnimator()
        recyclerView_last_match_event_fragment_1.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerView_last_match_event_fragment_1.adapter = lmeAdapter

    }

    private fun leagueIdHolderListener() {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                val loadingStatus0 =
                    withContext(Dispatchers.Default) {
                        (activity as MainActivity).viewModel.updateLoading(
                            true
                        )
                    }
                (activity as MainActivity).updateLoading(
                    loadingStatus0,
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    "start"
                )

                withContext(Dispatchers.IO) {
                    val leagueHolder =
                        (activity as MainActivity).viewModel.getLeagueIdHolder()!!
                    viewModel.requestLMEList(
                        object : ResponseListener {
                            override fun retrofitResponse(retrofitResponse: RetrofitResponse) {
                                responseLMEListener(retrofitResponse)
                            }
                        }, leagueHolder)

                }

            }

        }

    }

    private fun responseLMEListener(retrofitResponse: RetrofitResponse) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {

                withContext(Dispatchers.Default) {
                    if (retrofitResponse.isSuccess) {
                        val LMEData =
                            retrofitResponse.responseBody as LME

                        viewModel.initLMEList(LMEData.events)
                        withContext(Dispatchers.Main) { lmeAdapter.notifyDataSetChanged() }

                    } else {
                        withContext(Dispatchers.Main) {
                            viewModel.getMainActivity()
                                .popUp(retrofitResponse.message)
                        }
                    }

                }

                val loadingStatus1 =
                    withContext(Dispatchers.Default) {
                        (activity as MainActivity).viewModel.updateLoading(
                            false
                        )
                    }
                (activity as MainActivity).updateLoading(
                    loadingStatus1,
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    "stop"
                )

            }

        }

    }

    private fun selectedItemListener(lmeDetail: LMEDetail) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                val loadingStatus0 =
                    withContext(Dispatchers.Default) {
                        (activity as MainActivity).viewModel.updateLoading(
                            true
                        )
                    }
                (activity as MainActivity).updateLoading(
                    loadingStatus0,
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    "start"
                )

                withContext(Dispatchers.Default) {
                    viewModel.getMainActivity().viewModel.setSelectedEvent(
                        SelectedEvent(
                            lmeDetail.dateEvent,
                            lmeDetail.idHomeTeam,
                            lmeDetail.idAwayTeam,
                            lmeDetail.strHomeTeam,
                            lmeDetail.strAwayTeam,
                            lmeDetail.intHomeScore,
                            lmeDetail.intAwayScore,
                            lmeDetail.intHomeShots,
                            lmeDetail.intAwayShots,
                            lmeDetail.strHomeGoalDetails,
                            lmeDetail.strAwayGoalDetails,
                            lmeDetail.strHomeLineupGoalkeeper,
                            lmeDetail.strAwayLineupGoalkeeper,
                            lmeDetail.strHomeLineupDefense,
                            lmeDetail.strAwayLineupDefense,
                            lmeDetail.strHomeLineupMidfield,
                            lmeDetail.strAwayLineupMidfield,
                            lmeDetail.strHomeLineupForward,
                            lmeDetail.strAwayLineupForward,
                            lmeDetail.strHomeLineupSubstitutes,
                            lmeDetail.strAwayLineupSubstitutes
                        )
                    )
                }

                viewModel.getMainActivity()
                    .changeFragment2(R.id.frameLayout_activity_main_1, EventDetailFragment())

                val loadingStatus1 = withContext(Dispatchers.Default) {
                    (activity as MainActivity).viewModel.updateLoading(false)
                }
                (activity as MainActivity).updateLoading(
                    loadingStatus1,
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    "stop"
                )

            }

        }

    }

}
