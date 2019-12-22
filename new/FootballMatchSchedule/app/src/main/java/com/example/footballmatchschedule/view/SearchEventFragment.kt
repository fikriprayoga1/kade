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
import com.example.footballmatchschedule.model.apiresponse.SearchEventDetail
import com.example.footballmatchschedule.other.ResponseListener
import com.example.footballmatchschedule.other.recyclerviewadapter.SearchEventRecyclerViewAdapter
import com.example.footballmatchschedule.viewmodel.SearchEventViewModel
import kotlinx.android.synthetic.main.search_event_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchEventFragment : Fragment() {
    lateinit var searchEventAdapter: SearchEventRecyclerViewAdapter

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
                    "SearchEventFragment/46 : start"
                )

                withContext(Dispatchers.Default) {
                    viewModel.init(
                        (activity as MainActivity).viewModel.getUserRepository(),
                        (activity as MainActivity)
                    )

                }
                initRecyclerView()
                (activity as MainActivity).viewModel.getSearchEventList()?.
                    observe(thisContext, Observer { searchDataHolderListener(it) })

                val loadingStatus1 = withContext(Dispatchers.Default) {
                    (activity as MainActivity).viewModel.updateLoading(false)
                }
                (activity as MainActivity).updateLoading(
                    loadingStatus1,
                    "SearchEventFragment/116 : stop"
                )

            }

        }

    }

    private fun initRecyclerView() {
        searchEventAdapter = SearchEventRecyclerViewAdapter(
            context!!,
            viewModel.getSearchEventObjects(),
            object : SearchEventRecyclerViewAdapter.SearchEventListener {
                override fun itemDetail(searchEventDetail: SearchEventDetail) {

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
        recyclerView_search_event_fragment_1.adapter = searchEventAdapter

    }

    override fun onDestroy() {
        (activity as MainActivity).viewModel.setHasFragmentBackstack("SearchEvent", false)
        super.onDestroy()
    }

    private fun searchDataHolderListener(it: List<SearchEventDetail>?) {
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
                    "SearchEventFragment/46 : start"
                )

                withContext(Dispatchers.Default) { viewModel.initSearchEventList(it) }
                searchEventAdapter.notifyDataSetChanged()

                val loadingStatus1 = withContext(Dispatchers.Default) {
                    (activity as MainActivity).viewModel.updateLoading(false)
                }
                (activity as MainActivity).updateLoading(
                    loadingStatus1,
                    "SearchEventFragment/116 : stop"
                )

            }

        }

    }

}
