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
import com.example.footballmatchschedule.model.RetrofitResponse
import com.example.footballmatchschedule.model.apiresponse.Event
import com.example.footballmatchschedule.model.database.EventDatabase
import com.example.footballmatchschedule.util.helper.FMSHelper
import com.example.footballmatchschedule.util.helper.ResponseListener
import com.example.footballmatchschedule.util.recyclerviewadapter.EventRecyclerViewAdapter
import com.example.footballmatchschedule.viewmodel.LMEViewModel
import kotlinx.android.synthetic.main.last_match_event_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LMEFragment : Fragment() {
    private lateinit var eventAdapter: EventRecyclerViewAdapter

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

        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.Default) {
                    viewModel.init(
                        FMSHelper.getUserRepository()
                    )

                }
                initRecyclerView()
                FMSHelper.getLeagueIdHolderListener()
                    .observe(this@LMEFragment, Observer { leagueIdHolderListener() })

                (activity as MainActivity).stopLoading()

            }

        }

    }

    private fun initRecyclerView() {
        eventAdapter = EventRecyclerViewAdapter(
            context!!,
            viewModel.getEventObjects(),
            object : EventRecyclerViewAdapter.EventListener {
                override fun itemDetail(eventDatabase: EventDatabase) {
                    selectedItemListener(eventDatabase)

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
        recyclerView_last_match_event_fragment_1.adapter = eventAdapter

    }

    private fun leagueIdHolderListener() {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.IO) {
                    val leagueHolder =
                        FMSHelper.getLeagueIdHolder()!!
                    viewModel.requestLMEList(
                        object :
                            ResponseListener {
                            override fun retrofitResponse(retrofitResponse: RetrofitResponse) {
                                responseLMEListener(retrofitResponse)
                            }
                        }, leagueHolder
                    )

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
                            retrofitResponse.responseBody as Event

                        viewModel.initEventList(LMEData.events)
                        withContext(Dispatchers.Main) { eventAdapter.notifyDataSetChanged() }

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

    private fun selectedItemListener(eventDatabase: EventDatabase) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.Default) {
                    FMSHelper.setSelectedEvent(eventDatabase)
                    FMSHelper.setIsFromAPI(true)
                }

                (activity as MainActivity)
                    .changeFragment2(R.id.frameLayout_activity_main_1, EventDetailFragment())

                (activity as MainActivity).stopLoading()

            }

        }

    }

}
