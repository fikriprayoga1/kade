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
import com.example.footballmatchschedule.model.database.EventDatabase
import com.example.footballmatchschedule.util.recyclerviewadapter.EventRecyclerViewAdapter
import com.example.footballmatchschedule.viewmodel.FavoriteEventViewModel
import kotlinx.android.synthetic.main.favorite_event_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteEventFragment : Fragment() {
    private lateinit var eventAdapter: EventRecyclerViewAdapter

    companion object {
        fun newInstance() = FavoriteEventFragment()
    }

    private lateinit var viewModel: FavoriteEventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_event_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FavoriteEventViewModel::class.java)

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
                viewModel.getFavoriteEvent()?.observe(this@FavoriteEventFragment, Observer {
                    eventHandler(it)

                })


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

        recyclerView_favorite_event_fragment_1.layoutManager = mLayoutManager
        recyclerView_favorite_event_fragment_1.itemAnimator = DefaultItemAnimator()
        recyclerView_favorite_event_fragment_1.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerView_favorite_event_fragment_1.adapter = eventAdapter

    }

    private fun selectedItemListener(eventDatabase: EventDatabase) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.Default) {
                    viewModel.getMainActivity().viewModel.setSelectedEvent(eventDatabase)
                    viewModel.getMainActivity().viewModel.setIsFromAPI(false)
                }

                viewModel.getMainActivity()
                    .changeFragment2(R.id.frameLayout_activity_main_1, EventDetailFragment())

                (activity as MainActivity).stopLoading()

            }

        }

    }

    private fun eventHandler(eventList: List<EventDatabase>?) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.Default) {
                    viewModel.initEventList(eventList)
                    withContext(Dispatchers.Main) {
                        eventAdapter.notifyDataSetChanged()

                    }

                }

                (activity as MainActivity).stopLoading()

            }

        }

    }

}
