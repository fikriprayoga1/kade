package com.example.footballmatchschedule.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import com.example.footballmatchschedule.model.database.EventDatabase
import com.example.footballmatchschedule.other.recyclerviewadapter.EventRecyclerViewAdapter
import com.example.footballmatchschedule.viewmodel.AlarmViewModel
import kotlinx.android.synthetic.main.alarm_fragment.*
import kotlinx.android.synthetic.main.favorite_event_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlarmFragment : Fragment() {
    private lateinit var eventAdapter: EventRecyclerViewAdapter

    companion object {
        fun newInstance() = AlarmFragment()
    }

    private lateinit var viewModel: AlarmViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.alarm_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AlarmViewModel::class.java)

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
                viewModel.getAlarmEvent()?.observe(this@AlarmFragment, Observer {
                    eventHandler(it)

                })


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

        recyclerView_alarm_fragment_1.layoutManager = mLayoutManager
        recyclerView_alarm_fragment_1.itemAnimator = DefaultItemAnimator()
        recyclerView_alarm_fragment_1.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerView_alarm_fragment_1.adapter = eventAdapter

    }

    private fun selectedItemListener(eventDatabase: EventDatabase) {
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
                    viewModel.getMainActivity().viewModel.setSelectedEvent(eventDatabase)
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

    private fun eventHandler(eventList: List<EventDatabase>?) {
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
                    viewModel.initEventList(eventList, context!!)
                    withContext(Dispatchers.Main) {
                        eventAdapter.notifyDataSetChanged()

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

}
