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
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import com.example.footballmatchschedule.model.database.EventDatabase
import com.example.footballmatchschedule.other.recyclerviewadapter.EventRecyclerViewAdapter
import com.example.footballmatchschedule.viewmodel.SearchEventViewModel
import kotlinx.android.synthetic.main.search_event_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchEventFragment : Fragment() {
    lateinit var eventAdapter: EventRecyclerViewAdapter

    companion object {
        fun newInstance() = SearchEventFragment()
    }

    private lateinit var viewModel: SearchEventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_event_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchEventViewModel::class.java)

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
                (activity as MainActivity).viewModel.getEventList()
                    ?.observe(thisContext, Observer { searchDataHolderListener(it) })

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

        recyclerView_search_event_fragment_1.layoutManager = mLayoutManager
        recyclerView_search_event_fragment_1.itemAnimator = DefaultItemAnimator()
        recyclerView_search_event_fragment_1.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerView_search_event_fragment_1.adapter = eventAdapter

    }

    override fun onDestroy() {
        (activity as MainActivity).viewModel.setHasFragmentBackstack("Event", false)
        super.onDestroy()
    }

    private fun searchDataHolderListener(it: List<EventDetail>?) {
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

                withContext(Dispatchers.Default) { viewModel.initEventList(it) }
                eventAdapter.notifyDataSetChanged()

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
                    viewModel.getMainActivity().viewModel.setIsFromAPI(true)
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
